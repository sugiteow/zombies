package com.ailo.zombies.world;

import com.ailo.zombies.entity.Nothing;
import com.ailo.zombies.entity.Thing;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class World {
    private final Map<Coordinates, Thing> worldContents = new HashMap<>();

    public World(int worldSize) {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                worldContents.put(new Coordinates(x, y), new Nothing());
            }
        }
    }

    public Thing getContent(Coordinates coordinates) {
        Thing thing = worldContents.get(coordinates);
        if (thing == null) {
            throw new IllegalArgumentException(format("Unable to find Coordinates %s.  Please make sure the coordinates is valid", coordinates));
        }
        return thing;
    }

    public void addContent(Coordinates coordinates, Thing content) {
        Thing existingContent = getContent(coordinates);
        if (!existingContent.isNothing()) {
            throw new IllegalArgumentException(format("Unable to add [%s] to %s. Something is already there", content, coordinates));
        }

        worldContents.put(coordinates, content);
    }
}
