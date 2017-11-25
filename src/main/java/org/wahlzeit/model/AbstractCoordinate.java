package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {

    /**
     * Standard distance computes the cartesian distance.
     * Does a null check.
     * @param coord     Coordinate to compute distance to.
     * @return      Cartesian distance.
     * @throws IllegalArgumentException     if coord is null
     */
    @Override
    public double getDistance(Coordinate coord) throws IllegalArgumentException {
        this.assertIsNotNull(coord);
        return this.doGetDistance(coord);
    }

    /**
     * Standard distance computes the cartesian distance.
     * Converts this Coordinate to a CartesianCoordinate
     * as that class contains the cartesian distance implementation.
     * @param coord     Coordinate to compute distance to.
     * @return      Cartesian distance.
     */
    protected double doGetDistance(Coordinate coord) {
        return this.asCartesianCoordinate().doGetCartesianDistance(coord);
    }

    /**
     * Will forward to isEqual if the given Object is a Coordinate.
     * isEqual is implemented in the specific subclasses.
     * @param obj   Object to compare to
     * @return  true if obj is a Coordinate and the specific isEqual method is fulfilled.
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Coordinate) {
            isEqual = this.isEqual((Coordinate) obj);
        }
        return isEqual;
    }

    /**
     * Throw exception if coordinate is null.
     * @param coord     Coordinate to check.
     * @throws IllegalArgumentException     if coord is null
     */
    protected void assertIsNotNull(Coordinate coord) throws IllegalArgumentException {
        if (coord == null) {
            throw new IllegalArgumentException("Coordinate is null!");
        }
    }
}
