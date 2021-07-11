package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

public abstract class Thing {

    private final World world;
    private Coordinates currentCoordinates;

    public Thing(World world, Coordinates startingCoordinates) {
        this.world = world;
        this.currentCoordinates = startingCoordinates;
    }

    public void move(String movementInstruction) {
        Coordinates originalCoordinates = currentCoordinates;
        Coordinates finalCoordinates = currentCoordinates;
        MovementPattern movementPattern = getMovementPattern();

        for (Character instruction : movementPattern.translate(movementInstruction)) {
            finalCoordinates = movementPattern.applyTo(finalCoordinates, instruction, world);
        }

        world.remove(this, originalCoordinates);
        world.place(this, finalCoordinates);
        currentCoordinates = finalCoordinates;
    }

    abstract MovementPattern getMovementPattern();

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }
}
