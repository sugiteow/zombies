package com.ailo.zombies.entity;

import com.ailo.zombies.effect.StatusEffect;
import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItems;
import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItemsInOrder;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

class ThingTest {

    private MovementPattern movementPattern;
    private StubThing stubThing;
    private String movementInstruction;
    private World world;
    private Coordinates startingCoordinates;
    private Coordinates coordinatesAfterA;
    private Coordinates finalCoordinates;

    @BeforeEach
    public void setup() {
        List<Character> translatedInstructions = new ArrayList<Character>() {{
            add('A');
            add('B');
        }};

        movementPattern = mock(MovementPattern.class);
        startingCoordinates = new Coordinates(0, 0);
        coordinatesAfterA = new Coordinates(1, 1);
        finalCoordinates = new Coordinates(2, 2);
        world = mock(World.class);
        movementInstruction = "some-instruction";

        stubThing = new StubThing(world, startingCoordinates);
        stubThing.setMovementPattern(movementPattern);

        when(world.getContent(any())).thenReturn(newLinkedHashSet());
        when(movementPattern.translate(movementInstruction)).thenReturn(translatedInstructions);
        when(movementPattern.applyTo(startingCoordinates, 'A', world)).thenReturn(coordinatesAfterA);
        when(movementPattern.applyTo(coordinatesAfterA, 'B', world)).thenReturn(finalCoordinates);
    }

    @Test
    public void shouldAddThingToWorldOnConstructor() {
        Thing thing = new StubThing(world, startingCoordinates);

        verify(world).place(thing, startingCoordinates);
    }

    @Test
    public void shouldTranslateInstructionAndMove() {
        stubThing.move(movementInstruction);

        verify(movementPattern).translate(movementInstruction);
        verify(movementPattern).applyTo(startingCoordinates, 'A', world);
        verify(movementPattern).applyTo(coordinatesAfterA, 'B', world);
    }

    @Test
    public void shouldUpdateCurrentCoordinatesAndWorldContentAfterMoving() {
        stubThing.move(movementInstruction);

        assertThat(stubThing.getCurrentCoordinates(), is(finalCoordinates));
        verify(world).remove(stubThing);
        verify(world).place(stubThing, finalCoordinates);
    }

    @Test
    public void shouldApplyEffectToEverythingThatGotPasseWhenMovingAndResolveItInOrderOfPassing() {
        Thing affectedThing1 = mock(Thing.class);
        Thing affectedThing2 = mock(Thing.class);
        Thing affectedThing3 = mock(Thing.class);

        when(world.getContent(coordinatesAfterA)).thenReturn(newLinkedHashSet(asList(affectedThing1, affectedThing2)));
        when(world.getContent(finalCoordinates)).thenReturn(newLinkedHashSet(asList(affectedThing3)));

        stubThing.move(movementInstruction);

        assertThat(stubThing.getAffectedThings(), onlyHasItemsInOrder(affectedThing1, affectedThing2, affectedThing3));

        InOrder inOrder = inOrder(affectedThing1, affectedThing2, affectedThing3);

        inOrder.verify(affectedThing1).resolveStatusEffect();
        inOrder.verify(affectedThing2).resolveStatusEffect();
        inOrder.verify(affectedThing3).resolveStatusEffect();
    }

    @Test
    public void shouldOnlyApplyStatusEffectOnceToEachThingsPassedWhenMoving() {
        Thing affectedThing1 = mock(Thing.class);
        Thing affectedThing2 = mock(Thing.class);

        when(world.getContent(coordinatesAfterA)).thenReturn(newLinkedHashSet(asList(affectedThing1, affectedThing2)));
        when(world.getContent(finalCoordinates)).thenReturn(newLinkedHashSet(asList(affectedThing1)));

        stubThing.move(movementInstruction);

        assertThat(stubThing.getAffectedThings(), onlyHasItems(affectedThing1, affectedThing2));

        InOrder inOrder = inOrder(affectedThing1, affectedThing2);
        inOrder.verify(affectedThing1).resolveStatusEffect();
        inOrder.verify(affectedThing2).resolveStatusEffect();
    }

    @Test
    public void shouldNotApplyAndResolveStatusEffectOnThingsThatAlreadyHasExistingUnresolvedStatusEffect() {
        Thing thingWithUnresolvedStatusEffect = mock(Thing.class);
        Thing otherThing = mock(Thing.class);

        when(world.getContent(coordinatesAfterA)).thenReturn(newLinkedHashSet(asList(thingWithUnresolvedStatusEffect, otherThing)));
        when(thingWithUnresolvedStatusEffect.hasUnresolvedStatusEffect()).thenReturn(true);

        stubThing.move(movementInstruction);

        assertThat(stubThing.getAffectedThings(), onlyHasItems(otherThing));
        verify(otherThing).resolveStatusEffect();
        verify(thingWithUnresolvedStatusEffect, never()).resolveStatusEffect();
    }

    @Test
    public void shouldApplyStatusEffectAndClear() {
        StatusEffect statusEffect = mock(StatusEffect.class);

        stubThing.setStatusEffect(statusEffect);
        assertThat(stubThing.hasUnresolvedStatusEffect(), is(true));

        stubThing.resolveStatusEffect();
        assertThat(stubThing.hasUnresolvedStatusEffect(), is(false));
        verify(statusEffect).apply(stubThing);
    }
}