/*
 * Copyright (c) 2017 by Tobias Koch
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import org.wahlzeit.utils.asserts.DoubleAssert;
import org.wahlzeit.utils.asserts.ObjectAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate {

    /**
     * Methods subclasses have to implement.
     */
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract SphericCoordinate asSphericCoordinate();
    protected abstract void assertClassInvariants();

    /**
     * This number denotes the number of decimal places used to represent
     * coordinates precision and distance values.
     * All coordinate attributes and computed distances will be
     * rounded to DELTA decimal places.
     */
    public static final int DELTA = 10;

    /**
     * Radius in kilometer. Assume we are only working with spherical coordinates on earth
     * we can set a constant radius. We ignore the fact that the earth is not a perfect spherical.
     */
    public static final double EARTH_RADIUS = 6371.0d;

    /**
     * Standard distance computes the cartesian distance.
     * Forwards to {@link #getCartesianDistance(Coordinate)}.
     * @param coord     Coordinate to compute distance to.
     * @return      Cartesian distance.
     * @throws IllegalArgumentException     if coord is null or computed distance < 0
     */
    @Override
    public double getDistance(Coordinate coord) throws IllegalArgumentException {
        return this.getCartesianDistance(coord);
    }

    /**
     * Compute Euclidean distance to given coordinate.
     * Does a null check as precondition and a distance check as postcondition.
     * @param coord     Coordinate to compute distance to
     * @return  cartesian distance
     * @throws IllegalArgumentException     if coord is null or computed distance < 0
     */
    @Override
    public double getCartesianDistance(Coordinate coord) throws IllegalArgumentException {
        ObjectAssert.assertNotNull(coord, "Coordinate is null!");
        double distance = this.asCartesianCoordinate().doGetCartesianDistance(coord);
        DoubleAssert.assertPositiveZero(distance, "Invalid distance: " + distance + " < 0");
        return distance;
    }

    /**
     * Compute spheric distance to given coordinate.
     * Does a null check as precondition and a distance check as postcondition.
     * @param coord     Coordinate to compute distance to
     * @return  spheric distance
     * @throws IllegalArgumentException     if coord is null or computed distance < 0
     */
    @Override
    public double getSphericDistance(Coordinate coord) throws IllegalArgumentException {
        ObjectAssert.assertNotNull(coord, "Coordinate is null!");
        double distance = this.asSphericCoordinate().doGetSphericDistance(coord);
        DoubleAssert.assertPositiveZero(distance, "Invalid distance: " + distance + " < 0");
        return distance;
    }

    /**
     * Will forward to isEqual if the given Object is a Coordinate.
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
     * Compare this coordinate with the given one.
     * Does a null check but throws no exception.
     * Both coordinates will be converted to a CartesianCoordinate
     * both are equal if x, y and z are equal.
     * @param coord    Coordinate to compare to
     * @return  true if all attributes are equal
     */
    @Override
    public boolean isEqual(Coordinate coord) {
        boolean isEqual = false;
        if (coord != null) {
            CartesianCoordinate thisCart = this.asCartesianCoordinate();
            CartesianCoordinate tmp = coord.asCartesianCoordinate();
            isEqual = thisCart.getX() == tmp.getX()
                   && thisCart.getY() == tmp.getY()
                   && thisCart.getZ() == tmp.getZ();
        }
        return isEqual;
    }

    /**
     * Convert this coordinate object to a CartesianCoordinate.
     * Create hash code based on x, y and z.
     * @return hash code
     */
    @Override
    public int hashCode() {
        CartesianCoordinate thisCart = this.asCartesianCoordinate();
        return Objects.hash(thisCart.getX(), thisCart.getY(), thisCart.getZ());
    }

    /**
     * Will round a number to {@link #DELTA} decimal places.
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
}
