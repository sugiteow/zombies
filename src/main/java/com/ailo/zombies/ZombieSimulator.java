package com.ailo.zombies;

import com.ailo.zombies.entity.Creature;
import com.ailo.zombies.entity.Zombie;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

public class ZombieSimulator {
    private static final Logger LOGGER = Logger.getLogger(ZombieSimulator.class.getSimpleName());

    public SimulationResult run(Integer worldSize,
                                Coordinates initialZombieCoordinates,
                                List<Coordinates> initialCreatureCoordinates,
                                String movementInstruction) {
        World world = new World(worldSize);
        Zombie zombie = new Zombie(world, initialZombieCoordinates);
        initialCreatureCoordinates.forEach(coordinates -> new Creature(world, coordinates));

        zombie.move(movementInstruction);

        List<Coordinates> allZombiesCoordinates = world.getCoordinatesForAll(Zombie.class);
        List<Coordinates> allCreatureCoordinates = world.getCoordinatesForAll(Creature.class);

        LOGGER.info(format("Zombies Coordinates: %s", Arrays.toString(allZombiesCoordinates.toArray())));
        LOGGER.info(format("Creatures Coordinates: %s", Arrays.toString(allCreatureCoordinates.toArray())));

        return new SimulationResult(allZombiesCoordinates, allCreatureCoordinates);
    }
}
