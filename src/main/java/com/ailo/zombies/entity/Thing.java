package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.HashSet;
import java.util.Set;

public abstract class Thing {

    final World world;
    private Coordinates currentCoordinates;

    public Thing(World world, Coordinates startingCoordinates) {
        this.world = world;
        this.currentCoordinates = startingCoordinates;
        this.world.place(this, startingCoordinates);
    }

    public void move(String movementInstruction) {
        Coordinates originalCoordinates = currentCoordinates;
        Coordinates finalCoordinates = currentCoordinates;
        Set<Thing> passedThings = new HashSet<>();

        MovementPattern movementPattern = getMovementPattern();

        for (Character instruction : movementPattern.translate(movementInstruction)) {
            finalCoordinates = movementPattern.applyTo(finalCoordinates, instruction, world);
            Set<Thing> things = world.getContent(finalCoordinates);
            if (!things.isEmpty()) {
                passedThings.addAll(things);
            }
        }

        world.remove(this);
        world.place(this, finalCoordinates);
        currentCoordinates = finalCoordinates;

        this.applyEffectTo(passedThings, movementInstruction);
    }

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }

    abstract MovementPattern getMovementPattern();

    abstract void applyEffectTo(Set<Thing> things, String movementInstruction);
}
