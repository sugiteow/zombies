package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.Set;

import static com.ailo.zombies.movement.MovementPattern.IMMOBILE;

public class Zombie extends Thing {

    public Zombie(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return IMMOBILE;
    }

    @Override
    void applyEffectTo(Set<Thing> things) {

    }
}
