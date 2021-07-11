package com.ailo.zombies.entity;

import com.ailo.zombies.effect.StatusEffect;
import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.String.format;

public abstract class Thing {
    private static final Logger LOGGER = Logger.getLogger(Thing.class.getSimpleName());

    private final World world;
    private Coordinates currentCoordinates;
    private Integer tag;
    private StatusEffect statusEffect;

    public Thing(World world, Coordinates startingCoordinates) {
        this.world = world;
        this.currentCoordinates = startingCoordinates;
        this.world.place(this, startingCoordinates);
    }

    public void move(String movementInstruction) {
        Coordinates finalCoordinates = currentCoordinates;
        LinkedHashSet<Thing> affectedThings = new LinkedHashSet<>();
        MovementPattern movementPattern = getMovementPattern();

        for (Character instruction : movementPattern.translate(movementInstruction)) {
            finalCoordinates = movementPattern.applyTo(finalCoordinates, instruction, world);
            Set<Thing> things = world.getContent(finalCoordinates);
            if (!things.isEmpty()) {
                things.forEach(thing -> {
                    if (!thing.hasUnresolvedStatusEffect()) {
                        this.applyEffect(thing, movementInstruction);
                        affectedThings.add(thing);
                    }
                });
            }
        }

        world.remove(this);
        world.place(this, finalCoordinates);
        currentCoordinates = finalCoordinates;

        LOGGER.info(format("%s moved to %s", this, finalCoordinates));
        affectedThings.forEach(Thing::resolveStatusEffect);
    }

    public Coordinates getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getTag() {
        return tag;
    }

    public boolean setStatusEffect(StatusEffect statusEffect) {
        // if thing already have a status effect attached to it, don't overwrite it.
        if (this.statusEffect == null) {
            this.statusEffect = statusEffect;
            return true;
        }
        return false;
    }

    World getWorld() {
        return world;
    }

    void resolveStatusEffect() {
        if (statusEffect != null) {
            this.statusEffect.apply(this);
            this.statusEffect = null;
        }
    }

    public boolean hasUnresolvedStatusEffect() {
        return this.statusEffect != null;
    }

    @Override
    public String toString() {
        return format("%s %s", this.getClass().getSimpleName(), tag);
    }

    abstract MovementPattern getMovementPattern();

    abstract void applyEffect(Thing thing, String movementInstruction);
}
