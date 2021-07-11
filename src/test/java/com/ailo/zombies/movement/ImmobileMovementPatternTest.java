package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;

class ImmobileMovementPatternTest {

    private char anyInstruction;
    private ImmobileMovementPattern immobileMovementPattern;
    private Coordinates originalCoordinates;
    private World anyWorld;

    @BeforeEach
    public void setup() {
        anyWorld = new World(4);
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
        assertThat(immobileMovementPattern.applyTo(originalCoordinates, anyInstruction, anyWorld), is(originalCoordinates));
    }
}