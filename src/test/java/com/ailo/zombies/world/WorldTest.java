package com.ailo.zombies.world;

import com.ailo.zombies.entity.StubThing;
import com.ailo.zombies.entity.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItems;
import static java.lang.String.format;
import static java.util.Collections.emptySet;
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
                Set<Thing> content = world.getContent(coordinates);
                String message = format("Expecting world content on (%s,%s) to be Empty", x, y);

                assertThat(message, content.isEmpty(), is(true));
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
    public void shouldBeAbleToPlaceSomethingToTheWorld() {
        Coordinates coordinates = new Coordinates(1, 2);

        world.place(something, coordinates);

        assertThat(world.getContent(coordinates), onlyHasItems(something));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTryingToPlaceSomethingToNonExistentCoordinates() {
        assertThrows(IllegalArgumentException.class, () -> world.place(something, new Coordinates(-1, -1)));
        assertThrows(IllegalArgumentException.class, () -> world.place(something, new Coordinates(5, 4)));
        assertThrows(IllegalArgumentException.class, () -> world.place(something, new Coordinates(4, 5)));
    }

    @Test
    public void shouldAllowPlacingMultipleThingOnTheSameCoordinates() {
        Thing otherThing = mock(Thing.class);
        Coordinates coordinates = new Coordinates(1, 1);

        world.place(something, coordinates);
        world.place(otherThing, coordinates);

        assertThat(world.getContent(coordinates), onlyHasItems(something, otherThing));
    }

    @Test
    public void shouldRemoveThingFromCoordinates() {
        Coordinates coordinates = new Coordinates(1, 1);

        Thing aThing = new StubThing(world, coordinates);
        Thing otherThing = new StubThing(world, coordinates);

        world.place(aThing, coordinates);
        world.place(otherThing, coordinates);

        world.remove(aThing);
        assertThat(world.getContent(coordinates), onlyHasItems(otherThing));

        world.remove(otherThing);
        assertThat(world.getContent(coordinates), is(emptySet()));
    }

    @Test
    public void shouldDoNothingWhenTheThingToRemoveNoLongerExistsInTheWorld() {
        Coordinates coordinates = new Coordinates(1, 1);
        Thing aThing = new StubThing(world, coordinates);
        Thing otherThing = new StubThing(world, coordinates);

        world.place(aThing, coordinates);

        world.remove(otherThing);
        world.remove(aThing);
        world.remove(aThing);

        assertThat(world.getContent(coordinates), is(emptySet()));
    }
}