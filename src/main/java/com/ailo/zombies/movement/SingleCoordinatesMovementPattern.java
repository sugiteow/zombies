package com.ailo.zombies.movement;

import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.emptyList;

class SingleCoordinatesMovementPattern implements MovementPattern {

    private static final Map<Character, Coordinates> VALID_INSTRUCTIONS = new HashMap<Character, Coordinates>() {{
        put('L', new Coordinates(-1, 0));
        put('R', new Coordinates(1, 0));
        put('U', new Coordinates(0, -1));
        put('D', new Coordinates(0, 1));
    }};

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

        if (VALID_INSTRUCTIONS.keySet().containsAll(instructions)) {
            return instructions;
        }
        throw new IllegalArgumentException(format("Found an invalid instruction on [%s].  " +
                "The only accepted instructions are a combination of ['L','R','D','U']", instruction));
    }

    @Override
    public Coordinates applyTo(Coordinates originalCoordinates, Character instruction, World world) {
        if (originalCoordinates == null) {
            throw new IllegalArgumentException("originalCoordinates has to be specified");
        }

        if (instruction == null) {
            throw new IllegalArgumentException("instruction has to be specified");
        }

        if (world == null) {
            throw new IllegalArgumentException("world has to be specified");
        }

        if (!VALID_INSTRUCTIONS.containsKey(instruction)) {
            throw new IllegalArgumentException(format("invalid instruction [%s]", instruction));
        }

        return originalCoordinates.add(VALID_INSTRUCTIONS.get(instruction), world.getBoundaryCoordinates());
    }
}
