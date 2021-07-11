package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleCoordinatesMovementPatternTest {

    private SingleCoordinatesMovementPattern movementPattern;
    private World world;

    @BeforeEach
    public void setup() {
        movementPattern = new SingleCoordinatesMovementPattern();
        world = new World(5);
    }

    @Test
    public void shouldReturnListOfInstructionsWhenParsingValidInstruction() {
        assertThat(movementPattern.translate("RDLU"), is(asList('R', 'D', 'L', 'U')));
        assertThat(movementPattern.translate("ULRDULR"), is(asList('U', 'L', 'R', 'D', 'U', 'L', 'R')));
        assertThat(movementPattern.translate("RD"), is(asList('R', 'D')));
    }

    @Test
    public void shouldReturnEmptyListWhenGivenEmptyOrBlankOrNullInstructions() {
        assertThat(movementPattern.translate(""), is(empty()));
        assertThat(movementPattern.translate("   "), is(empty()));
        assertThat(movementPattern.translate(null), is(empty()));
    }

    @Test
    public void shouldTrimEmptySpaceOnTheFrontAndTheBackOfTheInstructions() {
        assertThat(movementPattern.translate("     RDLU    "), is(asList('R', 'D', 'L', 'U')));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGivenInvalidInstruction() {
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("!@#$"));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("RDU!D"));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("RDU D"));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("RDU D"));
    }

    @Test
    public void shouldChangeTheXCoordinatesByOneWhenApplyingLeftOrRightInstruction() {
        assertThat(movementPattern.applyTo(new Coordinates(3, 2), 'L', world), is(new Coordinates(2, 2)));
        assertThat(movementPattern.applyTo(new Coordinates(2, 2), 'L', world), is(new Coordinates(1, 2)));
        assertThat(movementPattern.applyTo(new Coordinates(3, 2), 'R', world), is(new Coordinates(4, 2)));
        assertThat(movementPattern.applyTo(new Coordinates(2, 2), 'R', world), is(new Coordinates(3, 2)));
    }

    @Test
    public void shouldReduceTheYCoordinatesByOneWhenApplyingUpOrDownInstruction() {
        assertThat(movementPattern.applyTo(new Coordinates(2, 3), 'U', world), is(new Coordinates(2, 2)));
        assertThat(movementPattern.applyTo(new Coordinates(2, 2), 'U', world), is(new Coordinates(2, 1)));
        assertThat(movementPattern.applyTo(new Coordinates(2, 3), 'D', world), is(new Coordinates(2, 4)));
        assertThat(movementPattern.applyTo(new Coordinates(2, 2), 'D', world), is(new Coordinates(2, 3)));
    }

}