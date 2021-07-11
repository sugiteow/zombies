package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.Set;

public class Creature extends Thing {
    public Creature(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return MovementPattern.IMMOBILE;
    }

    @Override
    void applyEffectTo(Set<Thing> things) {
        // doesn't do anything yet
    }
}
