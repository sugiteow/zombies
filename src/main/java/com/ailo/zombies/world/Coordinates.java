package com.ailo.zombies.world;

import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates add(Coordinates otherCoordinates, Coordinates boundaryCoordinates) {
        int newX = this.x + otherCoordinates.x;
        int newY = this.y + otherCoordinates.y;

        if (boundaryCoordinates != null) {
            if (newX < 0) {
                newX = boundaryCoordinates.x + (newX % boundaryCoordinates.x);
            } else if (newX >= boundaryCoordinates.x) {
                newX = newX % boundaryCoordinates.x;
            }

            if (newY < 0) {
                newY = boundaryCoordinates.y + (newY % boundaryCoordinates.y);
            } else if (newY >= boundaryCoordinates.y) {
                newY = newY % boundaryCoordinates.y;
            }
        }

        return new Coordinates(newX, newY);
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
