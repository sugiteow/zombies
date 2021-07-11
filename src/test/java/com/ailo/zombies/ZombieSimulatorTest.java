package com.ailo.zombies;

import com.ailo.zombies.world.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.ailo.zombies.matcher.CustomMatchers.onlyHasItemsInOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

class ZombieSimulatorTest {

    private ZombieSimulator zombieSimulator;

    @BeforeEach
    public void setup() {
        zombieSimulator = new ZombieSimulator();
    }

    @Test
    public void shouldRunSimulationAndProductCorrectResult() {
        int worldSize = 4;
        Coordinates initialZombieCoordinates = new Coordinates(3, 1);
        List<Coordinates> initialCreatureCoordinates = Arrays.asList(
                new Coordinates(0, 1),
                new Coordinates(1, 2),
                new Coordinates(1, 1)
        );

        SimulationResult result = zombieSimulator.run(worldSize, initialZombieCoordinates, initialCreatureCoordinates, "RDRU");

        assertThat(result.getAllZombiesCoordinates(), onlyHasItemsInOrder(
                new Coordinates(1, 1),
                new Coordinates(2, 1),
                new Coordinates(3, 2),
                new Coordinates(3, 1)
        ));

        assertThat(result.getAllCreatureCoordinates(), is(empty()));
    }
}