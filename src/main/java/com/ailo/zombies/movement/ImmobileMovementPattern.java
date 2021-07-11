package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.List;

import static java.util.Collections.emptyList;

class ImmobileMovementPattern implements MovementPattern {

    @Override
    public List<Character> translate(String instruction) {
        return emptyList();
    }

    @Override
    public Coordinates applyTo(Coordinates originalCoordinates, Character instruction, World world) {
        return originalCoordinates;
    }
}
