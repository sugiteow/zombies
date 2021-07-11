package com.ailo.zombies.effect;

import com.ailo.zombies.entity.InfectableThing;
import com.ailo.zombies.entity.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InfectionStatusEffectTest {

    private InfectionStatusEffect infectionStatusEffect;
    private String movementInstruction;

    @BeforeEach
    public void setup() {
        movementInstruction = "RDU";
        infectionStatusEffect = new InfectionStatusEffect(movementInstruction);
    }

    @Test
    public void shouldTurnInfectableThingToZombieWhenApplyingInfectionStatusEffectToIt() {
        InfectableThing infectableThing = mock(InfectableThing.class);
        infectionStatusEffect.apply(infectableThing);
        verify(infectableThing).turnToZombie(movementInstruction);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToApplyInfectionToNonInfectableThing() {
        Thing nonInfectableThing = mock(Thing.class);
        assertThrows(IllegalArgumentException.class, () -> infectionStatusEffect.apply(nonInfectableThing));
    }

}