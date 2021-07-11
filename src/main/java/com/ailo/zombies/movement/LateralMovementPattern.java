package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

class LateralMovementPattern implements MovementPattern {

    private static final Set<Character> VALID_INSTRUCTIONS = newHashSet('L', 'R', 'U', 'D');

    @Override
    public List<Character> translate(String instruction) {
        if (instruction == null) {
            return emptyList();
        }

        String trimmedInstruction = instruction.trim();

        if (trimmedInstruction.isEmpty()) {
            return emptyList();
        }

        List<Character> instructions = trimmedInstruction.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        if (VALID_INSTRUCTIONS.containsAll(instructions)) {
            return instructions;
        }
        throw new IllegalArgumentException(format("Found an invalid instruction on [%s].  " +
                "The only accepted instructions are a combination of ['L','R','D','U']", instruction));
    }

    @Override
    public Coordinates applyTo(Coordinates originalCoordinates, char instruction) {
        throw new NotImplementedException();
    }
}
