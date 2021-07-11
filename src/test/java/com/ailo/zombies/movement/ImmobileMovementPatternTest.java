package com.ailo.zombies.movement;

import com.ailo.zombies.entity.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ImmobileMovementPatternTest {

    private char anyInstruction;
    private ImmobileMovementPattern movementPattern;
    private Thing something;

    @BeforeEach
    public void setup() {
        something = mock(Thing.class);
        anyInstruction = 'A';
        movementPattern = new ImmobileMovementPattern();
    }

    @Test
    public void shouldDoNothingWhenAppliedToAnything() {
        movementPattern.applyTo(something, anyInstruction);
        verifyNoInteractions(something);
    }

    @Test
    public void shouldDoNothingWhenValidatingAnyInstruction() {
        movementPattern.applyTo(something, anyInstruction);
        verifyNoInteractions(something);
    }
}