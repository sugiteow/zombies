package com.ailo.zombies.world;

import com.ailo.zombies.entity.Creature;
import com.ailo.zombies.entity.StubThing;
import com.ailo.zombies.entity.Thing;
import com.ailo.zombies.entity.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItems;
import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItemsInOrder;
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
    public void shouldTagThingWithIdBasedOnNumberOfThatTypeOfThingWhenPlacingItInTheWorld() {
        Coordinates coordinates = new Coordinates(1, 1);
        Thing zombie1 = new Zombie(world, coordinates);
        Thing zombie2 = new Zombie(world, coordinates);

        Thing creature1 = new Creature(world, coordinates);
        Thing creature2 = new Creature(world, coordinates);
        Thing creature3 = new Creature(world, coordinates);

        assertThat(zombie1.getTag(), is(0));
        assertThat(zombie2.getTag(), is(1));
        assertThat(creature1.getTag(), is(0));
        assertThat(creature2.getTag(), is(1));
        assertThat(creature3.getTag(), is(2));
    }

    @Test
    public void shouldKeepIncrementingTagWhenNewThingIsPlacedOnTheWorldEvenWhenAnotherThingOfTheSameTypeIsRemoved() {
        Coordinates coordinates = new Coordinates(1, 1);
        Thing zombie1 = new Zombie(world, coordinates);
        world.remove(zombie1);

        Thing zombie2 = new Zombie(world, coordinates);
        assertThat(zombie2.getTag(), is(1));

        world.remove(zombie2);
        Thing zombie3 = new Zombie(world, coordinates);
        assertThat(zombie3.getTag(), is(2));
    }

    @Test
    public void shouldNotRetagThingWhenItAlreadyHasATag() {
        Coordinates coordinates = new Coordinates(1, 1);
        Thing zombie1 = new Zombie(world, coordinates);

        assertThat(zombie1.getTag(), is(0));

        Thing zombie2 = new Zombie(world, coordinates);
        world.place(zombie2, new Coordinates(2, 2));

        assertThat(zombie2.getTag(), is(1));
    }

    @Test
    public void shouldRemoveThingFromCoordinates() {
        Coordinates coordinates = new Coordinates(1, 1);

        Thing aThing = new StubThing(world, coordinates);
        Thing otherThing = new StubThing(world, coordinates);

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

        world.remove(otherThing);
        world.remove(aThing);
        world.remove(aThing);

        assertThat(world.getContent(coordinates), is(emptySet()));
    }

    @Test
    public void shouldGetAllCoordinatesForACertainTypeOfThingsSortedByItsTag() {
        Coordinates coordinate1 = new Coordinates(0, 0);
        Coordinates coordinate2 = new Coordinates(1, 2);
        Coordinates coordinate3 = new Coordinates(0, 1);
        Coordinates coordinate4 = new Coordinates(1, 1);
        Coordinates coordinate5 = new Coordinates(2, 2);
        Coordinates coordinate6 = new Coordinates(3, 1);

        new Zombie(world, coordinate1);
        new Zombie(world, coordinate2);
        new Zombie(world, coordinate3);

        new Creature(world, coordinate4);
        new Creature(world, coordinate5);
        new Creature(world, coordinate6);

        assertThat(world.getCoordinatesForAll(Zombie.class), onlyHasItemsInOrder(coordinate1, coordinate2, coordinate3));
        assertThat(world.getCoordinatesForAll(Creature.class), onlyHasItemsInOrder(coordinate4, coordinate5, coordinate6));
    }
}