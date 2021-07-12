package com.ailo.zombies;

import com.ailo.zombies.world.Coordinates;

import java.util.List;

public class SimulationResult {
    private final List<Coordinates> allZombiesCoordinates;
    private final List<Coordinates> allCreatureCoordinates;

    public SimulationResult(List<Coordinates> allZombiesCoordinates,
                            List<Coordinates> allCreatureCoordinates) {

        this.allZombiesCoordinates = allZombiesCoordinates;
        this.allCreatureCoordinates = allCreatureCoordinates;
    }

    public List<Coordinates> getAllZombiesCoordinates() {
        return allZombiesCoordinates;
    }

    public List<Coordinates> getAllCreatureCoordinates() {
        return allCreatureCoordinates;
    }
}
