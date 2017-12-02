package org.wahlzeit.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractCoordinate implements Coordinate {

    /**
     * Methods subclasses have to implement.
     */
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract double getCartesianDistance(Coordinate coord);

    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getSphericDistance(Coordinate coord);

    public abstract boolean isEqual(Coordinate coord);

    /**
     * This number denotes the number of decimals used to represent
     * coordinates precision and distance values.
     * All coordinate attributes and computed distances will be
     * rounded to DELTA decimals.
     */
    public static final int DELTA = 10;

    /**
     * Radius in kilometer. Assume we are only working with spherical coordinates on earth
     * we can set a constant radius. We ignore the fact that the earth is not a perfect spherical.
     */
    public static final double EARTH_RADIUS = 6371.0d;

    /**
     * Standard distance computes the cartesian distance.
     * Does a null check as precondition and a distance check as postcondition.
     * @param coord     Coordinate to compute distance to.
     * @return      Cartesian distance.
     * @throws IllegalArgumentException     if coord is null, distance < 0
     */
    @Override
    public double getDistance(Coordinate coord) throws IllegalArgumentException {
        this.assertIsNotNull(coord);
        double distance = this.doGetDistance(coord);
        this.assertValidDistance(distance);
        return distance;
    }

    /**
     * Standard distance computes the cartesian distance.
     * Converts this Coordinate to a CartesianCoordinate
     * as that class contains the cartesian distance implementation.
     * @param coord     Coordinate to compute distance to.
     * @return      Cartesian distance.
     */
    private double doGetDistance(Coordinate coord) {
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
     * Will round a number to {@link #DELTA} decimals.
     * Taken from: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places/2808648#2808648
     * Recent access: 01.12.2017, 19:21
     * @param value     Number to round.
     * @return  Rounded number.
     */
    protected static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(AbstractCoordinate.DELTA, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

    /**
     * Throw exception if distance is less 0.
     * @param distance  Distance to check
     * @throws IllegalArgumentException     if distance < 0
     */
    protected void assertValidDistance(double distance) throws IllegalArgumentException {
        if (distance < 0.0d) {
            throw new IllegalArgumentException("Invalid distance!");
        }
    }
}
