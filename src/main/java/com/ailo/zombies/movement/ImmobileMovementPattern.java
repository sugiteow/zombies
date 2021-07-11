package com.ailo.zombies.movement;

import com.ailo.zombies.entity.Thing;

public class ImmobileMovementPattern implements MovementPattern {

    @Override
    public void validate(String instruction) {
        // do nothing.  This thing can't move
    }

    @Override
    public void applyTo(Thing thing, char instruction) {
        // do nothing.  This thing can't move
    }
}
