package com.ailo.zombies.entity;

import com.ailo.zombies.movement.MovementPattern;
import com.ailo.zombies.world.Coordinates;
import com.ailo.zombies.world.World;

import java.util.Set;

public class StubThing extends Thing {

    private MovementPattern movementPattern;
    private Set<Thing> affectedThings;

    public StubThing(World world, Coordinates startingCoordinates) {
        super(world, startingCoordinates);
    }

    @Override
    MovementPattern getMovementPattern() {
        return movementPattern;
    }

    @Override
    void applyEffectTo(Set<Thing> things, String movementInstruction) {
        this.affectedThings = things;
    }

    public Set<Thing> getAffectedThings() {
        return affectedThings;
    }

    public void setMovementPattern(MovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }
}
