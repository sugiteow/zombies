package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItemsInOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WalkingMovementPatternTest {

    private WalkingMovementPattern movementPattern;
    private World world;

    @BeforeEach
    public void setup() {
        movementPattern = new WalkingMovementPattern();
        world = new World(5);
    }

    @Test
    public void shouldReturnListOfInstructionsWhenParsingValidInstruction() {
        assertThat(movementPattern.translate("RDLU"), onlyHasItemsInOrder('R', 'D', 'L', 'U'));
        assertThat(movementPattern.translate("ULRDULR"), onlyHasItemsInOrder('U', 'L', 'R', 'D', 'U', 'L', 'R'));
        assertThat(movementPattern.translate("RD"), onlyHasItemsInOrder('R', 'D'));
    }

    @Test
    public void shouldReturnEmptyListWhenGivenEmptyOrBlankOrNullInstructions() {
        assertThat(movementPattern.translate(""), is(empty()));
        assertThat(movementPattern.translate("   "), is(empty()));
        assertThat(movementPattern.translate(null), is(empty()));
    }

    @Test
    public void shouldTrimEmptySpaceOnTheFrontAndTheBackOfTheInstructions() {
        assertThat(movementPattern.translate("     RDLU    "), onlyHasItemsInOrder('R', 'D', 'L', 'U'));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGivenInvalidInstruction() {
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("!@#$"));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("RDU!D"));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("RDU D"));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.translate("RDU D"));
    }

    @Test
    public void shouldReduceXCoordinatesByOneWhenApplyingLeftInstruction() {
        Coordinates originalCoordinates = mock(Coordinates.class);
        Coordinates expectedCoordinates = new Coordinates(2, 1);

        when(originalCoordinates.add(any(), any())).thenReturn(expectedCoordinates);

        Coordinates resultingCoordinates = movementPattern.applyTo(originalCoordinates, 'L', world);

        assertThat(resultingCoordinates, is(expectedCoordinates));
        verify(originalCoordinates).add(new Coordinates(-1, 0), world.getBoundaryCoordinates());
    }

    @Test
    public void shouldAddXCoordinatesByOneWhenApplyingRightInstruction() {
        Coordinates originalCoordinates = mock(Coordinates.class);
        Coordinates expectedCoordinates = new Coordinates(2, 1);

        when(originalCoordinates.add(any(), any())).thenReturn(expectedCoordinates);

        Coordinates resultingCoordinates = movementPattern.applyTo(originalCoordinates, 'R', world);

        assertThat(resultingCoordinates, is(expectedCoordinates));
        verify(originalCoordinates).add(new Coordinates(1, 0), world.getBoundaryCoordinates());
    }

    @Test
    public void shouldReduceTheYCoordinatesByOneWhenApplyingUpInstruction() {
        Coordinates originalCoordinates = mock(Coordinates.class);
        Coordinates expectedCoordinates = new Coordinates(2, 1);

        when(originalCoordinates.add(any(), any())).thenReturn(expectedCoordinates);

        Coordinates resultingCoordinates = movementPattern.applyTo(originalCoordinates, 'U', world);

        assertThat(resultingCoordinates, is(expectedCoordinates));
        verify(originalCoordinates).add(new Coordinates(0, -1), world.getBoundaryCoordinates());
    }

    @Test
    public void shouldIncreaseTheYCoordinatesByOneWhenApplyingDownInstruction() {
        Coordinates originalCoordinates = mock(Coordinates.class);
        Coordinates expectedCoordinates = new Coordinates(2, 1);

        when(originalCoordinates.add(any(), any())).thenReturn(expectedCoordinates);

        Coordinates resultingCoordinates = movementPattern.applyTo(originalCoordinates, 'D', world);

        assertThat(resultingCoordinates, is(expectedCoordinates));
        verify(originalCoordinates).add(new Coordinates(0, 1), world.getBoundaryCoordinates());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToApplyInstructionOnNullCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> movementPattern.applyTo(null, 'D', world));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToApplyNullInstruction() {
        assertThrows(IllegalArgumentException.class, () -> movementPattern.applyTo(new Coordinates(0, 0), null, world));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToApplyInstructionToNullWorld() {
        assertThrows(IllegalArgumentException.class, () -> movementPattern.applyTo(new Coordinates(0, 0), 'D', null));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToApplyInvalidInstruction() {
        assertThrows(IllegalArgumentException.class, () -> movementPattern.applyTo(new Coordinates(0, 0), 'X', world));
        assertThrows(IllegalArgumentException.class, () -> movementPattern.applyTo(new Coordinates(0, 0), ' ', world));
    }

}