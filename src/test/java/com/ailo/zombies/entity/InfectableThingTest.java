package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class InfectableThingTest {

    private InfectableThing infectableThing;
    private World world;
    private String movementInstruction;

    private Coordinates expectedFinalCoordinates;

    @BeforeEach
    public void setup() {
        world = new World(5);
        movementInstruction = "RD";
        infectableThing = new StubInfectableThing(world, new Coordinates(0, 1));
        expectedFinalCoordinates = new Coordinates(1, 2);
    }

    @Test
    public void shouldRemoveInfectableThingFromTheWorldAndCreateNewZombieAndMoveWhenTurnedToZombie() {
        Zombie resultingZombie = infectableThing.turnToZombie(movementInstruction);

        assertThat(resultingZombie.getCurrentCoordinates(), is(expectedFinalCoordinates));
        assertThat(world.getContent(expectedFinalCoordinates), onlyHasItems(resultingZombie));
    }

    private class StubInfectableThing extends InfectableThing {

        public StubInfectableThing(World world, Coordinates startingCoordinates) {
            super(world, startingCoordinates);
        }

        @Override
        MovementPattern getMovementPattern() {
            return null;
        }

        @Override
        void applyEffect(Thing thing, String movementInstruction) {
        }
    }
}