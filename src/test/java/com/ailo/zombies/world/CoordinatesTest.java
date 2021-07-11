package com.ailo.zombies.world;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CoordinatesTest {

    private Coordinates topLeftCoordinates;
    private Coordinates boundaryCoordinates;
    private Coordinates bottomRightCoordinates;

    @BeforeEach
    public void setup() {
        boundaryCoordinates = new Coordinates(5, 5);
        topLeftCoordinates = new Coordinates(0, 0);
        bottomRightCoordinates = new Coordinates(4, 4);
    }

    @Test
    public void shouldAddNewCoordinateAndProduceANewCoordinate() {
        Coordinates originalCoordinates = new Coordinates(1, 1);
        Coordinates otherCoordinates = new Coordinates(-1, 1);
        Coordinates resultingCoordinates = originalCoordinates.add(otherCoordinates, null);

        assertThat(resultingCoordinates, is(new Coordinates(0, 2)));
    }

    @Test
    public void shouldCircleAroundWhenAddingCoordinatesCrossesTheBoundaryCoordinates() {
        assertThat(topLeftCoordinates.add(new Coordinates(0, -1), boundaryCoordinates), is(new Coordinates(0, 4)));
        assertThat(topLeftCoordinates.add(new Coordinates(-1, 0), boundaryCoordinates), is(new Coordinates(4, 0)));
        assertThat(bottomRightCoordinates.add(new Coordinates(0, 1), boundaryCoordinates), is(new Coordinates(4, 0)));
        assertThat(bottomRightCoordinates.add(new Coordinates(1, 0), boundaryCoordinates), is(new Coordinates(0, 4)));
    }

    @Test
    public void shouldAllowForMovementThatCircleAroundMultipleTimes() {
        assertThat(topLeftCoordinates.add(new Coordinates(0, -8), boundaryCoordinates), is(new Coordinates(0, 2)));
        assertThat(topLeftCoordinates.add(new Coordinates(-8, 0), boundaryCoordinates), is(new Coordinates(2, 0)));
        assertThat(bottomRightCoordinates.add(new Coordinates(0, 8), boundaryCoordinates), is(new Coordinates(4, 2)));
        assertThat(bottomRightCoordinates.add(new Coordinates(8, 0), boundaryCoordinates), is(new Coordinates(2, 4)));
    }

}