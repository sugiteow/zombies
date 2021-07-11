package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.String.format;

public abstract class Thing {
    static final Logger LOGGER = Logger.getLogger(Thing.class.getSimpleName());

    private final World world;
    private Coordinates currentCoordinates;
    private Integer tag;

    public Thing(World world, Coordinates startingCoordinates) {
        this.world = world;
        this.currentCoordinates = startingCoordinates;
        this.world.place(this, startingCoordinates);
    }

    public void move(String movementInstruction) {
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

        LOGGER.info(format("%s moved to %s", this, finalCoordinates));

        this.applyEffectTo(passedThings, movementInstruction);
    }

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }

    World getWorld() {
        return world;
    }

    abstract MovementPattern getMovementPattern();

    abstract void applyEffectTo(Set<Thing> things, String movementInstruction);

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return format("%s %s", this.getClass().getSimpleName(), tag);
    }
}
