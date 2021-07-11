package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;

import java.util.List;

public interface MovementPattern {
    public static final MovementPattern LATERAL_MOVEMENT_PATTERN = new LateralMovementPattern();
    public static final MovementPattern IMMOBILE_MOVEMENT_PATTERN = new ImmobileMovementPattern();

    List<Character> translate(String movementInstruction);
    Coordinates applyTo(Coordinates originalCoordinates, char movementInstruction);
}
