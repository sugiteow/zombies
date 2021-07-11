package com.ailo.zombies.world;

import com.ailo.zombies.entity.Thing;

import java.util.*;

import static java.lang.String.format;

public class World {
    private final Map<Class<? extends Thing>, Integer> numberOfThings = new HashMap<>();
    private final Map<Coordinates, Set<Thing>> worldContents = new HashMap<>();
    private final Coordinates boundaryCoordinates;

    public World(int worldSize) {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                worldContents.put(new Coordinates(x, y), new HashSet<>());
            }
        }
        this.boundaryCoordinates = new Coordinates(worldSize, worldSize);
    }

    public Set<Thing> getContent(Coordinates coordinates) {
        Set<Thing> things = worldContents.get(coordinates);
        if (things == null) {
            throw new IllegalArgumentException(format("Unable to find Coordinates %s.  Please make sure the coordinates is valid", coordinates));
        }
        return things;
    }

    public void place(Thing thing, Coordinates coordinates) {
        getContent(coordinates).add(thing);

        if (thing.getTag() == null) {
            Integer numberOfThing = numberOfThings.getOrDefault(thing.getClass(), 0);
            thing.setTag(numberOfThing);
            numberOfThings.put(thing.getClass(), ++numberOfThing);
        }
    }

    public Coordinates getBoundaryCoordinates() {
        return boundaryCoordinates;
    }

    public void remove(Thing thing) {
        Set<Thing> contents = getContent(thing.getCurrentCoordinates());
        contents.remove(thing);
    }

    public List<Coordinates> getCoordinatesForAll(Class<? extends Thing> type) {
        List<Coordinates> coordinates = new ArrayList<>();
        worldContents.entrySet().forEach(entry -> entry.getValue()
                .stream()
                .filter(thing -> type.isAssignableFrom(thing.getClass()))
                .sorted(Comparator.comparing(Thing::getTag))
                .forEach(content -> coordinates.add(entry.getKey())));

        return coordinates;
    }
}
