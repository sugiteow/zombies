package com.ailo.zombies.world;

import com.ailo.zombies.entity.Thing;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

public class World {
    private final Map<Coordinates, Set<Thing>> worldContents = new HashMap<>();

    public World(int worldSize) {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                worldContents.put(new Coordinates(x, y), new HashSet<>());
            }
        }
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
    }
}
