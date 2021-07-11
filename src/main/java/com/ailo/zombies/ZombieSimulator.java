package com.ailo.zombies;

import com.ailo.zombies.entity.Creature;
import com.ailo.zombies.entity.Zombie;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.List;
import java.util.logging.Logger;

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

        return new SimulationResult(allZombiesCoordinates, allCreatureCoordinates);
    }
}
