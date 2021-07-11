package com.ailo.zombies.entity;

import com.ailo.zombies.effect.InfectionStatusEffect;
import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.logging.Logger;

import static com.ailo.zombies.movement.MovementPattern.WALKING;
import static java.lang.String.format;

public class Zombie extends Thing {

    private static final Logger LOGGER = Logger.getLogger(Zombie.class.getSimpleName());

    public Zombie(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return WALKING;
    }

    @Override
    void applyEffect(Thing otherThing, String movementInstruction) {
        if (otherThing instanceof InfectableThing) {
            if (otherThing.setStatusEffect(new InfectionStatusEffect(movementInstruction))) {
                LOGGER.info(format("%s is infecting %s", this, otherThing));
            }
        }
    }
}
