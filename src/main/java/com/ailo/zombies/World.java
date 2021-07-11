package com.ailo.zombies;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class World {
    private final Map<Coordinates, WorldContent> worldContents = new HashMap<>();

    public World(int worldSize) {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                worldContents.put(new Coordinates(x,y), new EmptyContent());
            }
        }
    }

    public WorldContent getContent(Coordinates coordinates) {
        WorldContent worldContent = worldContents.get(coordinates);
        if (worldContent == null) {
            throw new IllegalArgumentException(format("Unable to find Coordinates %s.  Please make sure the coordinates is valid", coordinates));
        }
        return worldContent;
    }
}
