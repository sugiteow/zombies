package com.ailo.zombies.world;

import com.ailo.zombies.entity.Nothing;
import com.ailo.zombies.entity.Thing;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class WorldTest {

    public World world;
    private int worldSize;
    private Thing something;

    @BeforeEach
    public void setup() {
        something = mock(Thing.class);

        worldSize = 5;
        world = new World(worldSize);
    }

    @Test
    public void shouldInitialiseAllCoordinatesWithNothingObjectWhenWorldIsFirstCreated() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                Coordinates coordinates = new Coordinates(x, y);
                Thing content = world.getContent(coordinates);
                String message = format("Expecting world content on (%s,%s) to be Nothing", x, y);

                assertThat(message, content.isNothing(), is(true));
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

    @Test
    public void shouldBeAbleToAddContentToTheWorld() {
        Coordinates coordinates = new Coordinates(1, 2);
        Coordinates otherCoordinates = new Coordinates(2, 2);

        world.addContent(coordinates, something);

        assertThat(world.getContent(coordinates), is(something));
        assertThat(world.getContent(otherCoordinates), instanceOf(Nothing.class));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToAddContentToNonExistentCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> world.addContent(new Coordinates(-1, -1), something));
        assertThrows(IllegalArgumentException.class, () -> world.addContent(new Coordinates(5, 4), something));
        assertThrows(IllegalArgumentException.class, () -> world.addContent(new Coordinates(4, 5), something));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToAddContentToNonEmptyCoordinates() {
        Thing otherThing = mock(Thing.class);
        Coordinates coordinates = new Coordinates(1, 1);

        world.addContent(coordinates, this.something);
        assertThrows(IllegalArgumentException.class, () -> world.addContent(coordinates, otherThing));
    }
}