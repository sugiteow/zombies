package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.List;

public interface MovementPattern {
    public static final MovementPattern LATERAL_MOVEMENT_PATTERN = new SingleCoordinatesMovementPattern();
    public static final MovementPattern IMMOBILE_MOVEMENT_PATTERN = new ImmobileMovementPattern();

    List<Character> translate(String movementInstruction);
    Coordinates applyTo(Coordinates originalCoordinates, char instruction, World world);
}
