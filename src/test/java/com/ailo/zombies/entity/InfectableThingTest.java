package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class InfectableThingTest {

    private InfectableThing infectableThing;
    private World world;
    private Coordinates currentCoordinates;
    private String movementInstruction;

    @BeforeEach
    public void setup() {
        world = new World(5);
        currentCoordinates = new Coordinates(0, 1);
        movementInstruction = "";
        infectableThing = new TestInfectableThing(world, currentCoordinates);
    }

    @Test
    public void shouldRemoveSelfFromTheWorldAndCreateNewZombieAndMoveFromCurrentCoordinates() {
        Zombie resultingZombie = infectableThing.turnToZombie(movementInstruction);

        assertThat(resultingZombie.getCurrentCoordinates(), is(currentCoordinates));
        assertThat(world.getContent(currentCoordinates), onlyHasItems(resultingZombie));
    }

    private class TestInfectableThing extends InfectableThing {

        public TestInfectableThing(World world, Coordinates startingCoordinates) {
            super(world, startingCoordinates);
        }

        @Override
        MovementPattern getMovementPattern() {
            return null;
        }

        @Override
        void applyEffectTo(Set<Thing> things, String movementInstruction) {

        }
    }
}