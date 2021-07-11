package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

class ImmobileMovementPattern implements MovementPattern {

    @Override
    public List<Character> translate(String instruction) {
        return emptyList();
    }

    @Override
    public Coordinates applyTo(Coordinates originalCoordinates, char instruction) {
        return originalCoordinates;
    }
}
