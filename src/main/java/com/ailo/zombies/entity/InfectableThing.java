package com.ailo.zombies.entity;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

public abstract class InfectableThing extends Thing {
    public InfectableThing(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    public Zombie turnToZombie(String movementInstruction) {
        getWorld().remove(this);
        Zombie zombie = new Zombie(getWorld(), getCurrentCoordinates());
        zombie.move(movementInstruction);
        return zombie;
    }
}
