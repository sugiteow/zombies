package com.ailo.zombies.effect;

import com.ailo.zombies.entity.InfectableThing;
import com.ailo.zombies.entity.Thing;

import java.util.Objects;

import static java.lang.String.format;

public class InfectionStatusEffect implements StatusEffect {

    private String movementInstruction;

    public InfectionStatusEffect(String movementInstruction) {
        this.movementInstruction = movementInstruction;
    }

    @Override
    public void apply(Thing thing) {
        if (thing instanceof InfectableThing) {
            ((InfectableThing) thing).turnToZombie(movementInstruction);
        } else {
            throw new IllegalArgumentException(format("[%s] is not infectable", thing));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfectionStatusEffect that = (InfectionStatusEffect) o;
        return Objects.equals(movementInstruction, that.movementInstruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movementInstruction);
    }
}
