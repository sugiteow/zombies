package com.ailo.zombies.movement;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LateralMovementPatternTest {

    private LateralMovementPattern movementPattern;

    @BeforeEach
    public void setup() {
        movementPattern = new LateralMovementPattern();
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

}