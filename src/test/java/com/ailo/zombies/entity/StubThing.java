package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.ArrayList;
import java.util.List;

public class StubThing extends Thing {

    private MovementPattern movementPattern;
    private final List<Thing> affectedThings = new ArrayList<>();

    public StubThing(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return movementPattern;
    }

    @Override
    void applyEffect(Thing thing, String movementInstruction) {
        if (this.affectedThings.contains(thing)) {
        } else {
            this.affectedThings.add(thing);
        }
    }

    public List<Thing> getAffectedThings() {
        return affectedThings;
    }

    public void setMovementPattern(MovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }
}
