package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

class ImmobileMovementPatternTest {

    private char anyInstruction;
    private ImmobileMovementPattern immobileMovementPattern;
    private Coordinates originalCoordinates;

    @BeforeEach
    public void setup() {
        originalCoordinates = new Coordinates(5, 5);
        anyInstruction = 'A';
        immobileMovementPattern = new ImmobileMovementPattern();
    }

    @Test
    public void shouldReturnEmptyListWhenTranslatingAnyInstruction() {
        assertThat(immobileMovementPattern.translate("any-instructions"), is(empty()));
    }

    @Test
    public void shouldReturnOriginalCoordinateWhenApplyingAnyInstruction() {
        assertThat(immobileMovementPattern.applyTo(originalCoordinates, anyInstruction), is(originalCoordinates));
    }
}