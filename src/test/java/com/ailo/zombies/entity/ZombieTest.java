package com.ailo.zombies.entity;

import com.ailo.zombies.effect.InfectionStatusEffect;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ZombieTest {
    private InfectableThing infectableThing;
    private Thing nonInfectableThing;

    private Zombie zombie;
    private String movementInstruction;
    private World world;

    @BeforeEach
    public void setup() {
        world = mock(World.class);
        infectableThing = mock(InfectableThing.class);
        nonInfectableThing = mock(Thing.class);
        movementInstruction = "RDU";
        zombie = new Zombie(world, new Coordinates(1, 1));
    }

    @Test
    public void shouldSetInfectionStatusEffectToInfectableThing() {
        zombie.applyEffect(infectableThing, movementInstruction);
        verify(infectableThing).setStatusEffect(new InfectionStatusEffect(movementInstruction));
    }

    @Test
    public void shouldNotDoAnythingToNoninfectableThing() {
        zombie.applyEffect(nonInfectableThing, movementInstruction);
        verifyNoInteractions(nonInfectableThing);
    }
}