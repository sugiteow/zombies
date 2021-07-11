package com.ailo.zombies.movement;

import com.ailo.zombies.entity.Thing;

public interface MovementPattern {
    void validate(String movementInstruction);
    void applyTo(Thing thing, char movementInstruction);
}
