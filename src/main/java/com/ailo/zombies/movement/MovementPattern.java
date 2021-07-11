package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.List;

public interface MovementPattern {
    MovementPattern WALKING = new WalkingMovementPattern();
    MovementPattern IMMOBILE = new ImmobileMovementPattern();

    List<Character> translate(String movementInstruction);
    Coordinates applyTo(Coordinates originalCoordinates, Character instruction, World world);
}
