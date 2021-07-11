package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItems;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

class ThingTest {

    private MovementPattern movementPattern;
    private StubThing thing;
    private String movementInstruction;
    private List<Character> translatedInstructions;
    private World world;
    private Coordinates startingCoordinates;
    private Coordinates coordinatesAfterA;
    private Coordinates finalCoordinates;

    @BeforeEach
    public void setup() {
        movementPattern = mock(MovementPattern.class);
        startingCoordinates = new Coordinates(0, 0);
        coordinatesAfterA = new Coordinates(1, 1);
        finalCoordinates = new Coordinates(2, 2);
        world = mock(World.class);

        movementInstruction = "some-instruction";
        translatedInstructions = new ArrayList<Character>() {{
            add('A');
            add('B');
        }};

        thing = new StubThing(world, startingCoordinates);

        when(world.getContent(any())).thenReturn(emptySet());
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
        thing.move(movementInstruction);

        verify(movementPattern).translate(movementInstruction);
        verify(movementPattern).applyTo(startingCoordinates, 'A', world);
        verify(movementPattern).applyTo(coordinatesAfterA, 'B', world);
    }

    @Test
    public void shouldUpdateCurrentCoordinatesAndWorldContentAfterMoving() {
        thing.move(movementInstruction);

        assertThat(thing.getCurrentCoordinates(), is(finalCoordinates));
        verify(world).remove(thing);
        verify(world).place(thing, finalCoordinates);
    }

    @Test
    public void shouldApplyEffectToEverythingThatGotPassedWhenMoving() {
        Thing passedThing1 = new StubThing(world, coordinatesAfterA);
        Thing passedThing2 = new StubThing(world, coordinatesAfterA);
        Thing passedThing3 = new StubThing(world, finalCoordinates);

        when(world.getContent(coordinatesAfterA)).thenReturn(newHashSet(passedThing1, passedThing2));
        when(world.getContent(finalCoordinates)).thenReturn(newHashSet(passedThing3));

        thing.move(movementInstruction);

        assertThat(thing.getAffectedThings(), onlyHasItems(passedThing1, passedThing2, passedThing3));
    }

    @Test
    public void shouldOnlyApplyEffectOnceToEachThingsPassedWhenMoving() {
        Thing passedThing1 = new StubThing(world, coordinatesAfterA);
        Thing passedThing2 = new StubThing(world, coordinatesAfterA);

        when(world.getContent(coordinatesAfterA)).thenReturn(newHashSet(passedThing1, passedThing2));
        when(world.getContent(finalCoordinates)).thenReturn(newHashSet(passedThing1));

        thing.move(movementInstruction);

        assertThat(thing.getAffectedThings(), onlyHasItems(passedThing1, passedThing2));
    }

}