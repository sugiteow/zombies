package com.ailo.zombies.entity;

import com.ailo.zombies.effect.InfectionStatusEffect;
import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

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
    void applyEffect(Thing thing, String movementInstruction) {
        if (thing instanceof InfectableThing) {
            thing.setStatusEffect(new InfectionStatusEffect(movementInstruction));
        }
    }
}
