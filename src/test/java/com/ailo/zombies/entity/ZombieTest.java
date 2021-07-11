package com.ailo.zombies.entity;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ZombieTest {

    private InfectableThing creature1;
    private InfectableThing creature2;
    private Thing otherThing;

    private Zombie zombie;
    private String movementInstruction;
    private World world;

    @BeforeEach
    public void setup() {
        world = mock(World.class);

        creature1 = mock(InfectableThing.class);
        creature2 = mock(InfectableThing.class);
        otherThing = mock(Thing.class);

        movementInstruction = "RDU";

        zombie = new Zombie(world, new Coordinates(1, 1));
    }

    @Test
    public void shouldTurnCreatureToZombieWhenApplyingEffectToIt() {
        zombie.applyEffectTo(newHashSet(creature1, creature2), movementInstruction);

        verify(creature1).turnToZombie(movementInstruction);
        verify(creature2).turnToZombie(movementInstruction);
    }
}