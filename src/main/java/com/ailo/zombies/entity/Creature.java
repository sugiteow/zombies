package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

public class Creature extends InfectableThing {
    public Creature(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return MovementPattern.IMMOBILE;
    }

    @Override
    void applyEffect(Thing thing, String movementInstruction) {
    }
}
