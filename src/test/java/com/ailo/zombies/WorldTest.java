package com.ailo.zombies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WorldTest {

    public World world;
    private int worldSize;

    @BeforeEach
    public void setup() {
        worldSize = 5;
        world = new World(worldSize);
    }

    @Test
    public void shouldInitialiseAllCoordinatesWithNullContentWhenWorldIsFirstCreated() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                Coordinates coordinates = new Coordinates(x, y);
                WorldContent worldContent = world.getContent(coordinates);
                String message = format("Expecting world content on (%s,%s) to be null", x, y);

                assertThat(message, worldContent, instanceOf(EmptyContent.class));
            }
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToGetContentOfNonExistentCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> world.getContent(new Coordinates(-1, -1)));
        assertThrows(IllegalArgumentException.class, () -> world.getContent(new Coordinates(-2, -10)));
        assertThrows(IllegalArgumentException.class, () -> world.getContent(new Coordinates(5, 4)));
        assertThrows(IllegalArgumentException.class, () -> world.getContent(new Coordinates(4, 5)));
        assertThrows(IllegalArgumentException.class, () -> world.getContent(new Coordinates(5, 5)));
    }

}