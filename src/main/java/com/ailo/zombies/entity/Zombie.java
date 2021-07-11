package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.Set;

import static com.ailo.zombies.movement.MovementPattern.WALKING;

public class Zombie extends Thing {

    public Zombie(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return WALKING;
    }

    @Override
    void applyEffectTo(Set<Thing> things, String movementInstruction) {
        things.forEach(thing -> {
            if (thing instanceof InfectableThing) {
                ((InfectableThing) thing).turnToZombie(movementInstruction);
            }
        });
    }
}
