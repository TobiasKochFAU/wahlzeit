/*
 * Copyright (c) 2006-2009 by Tobias Koch
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

import java.util.Objects;

/**
 * Class representing a 3d-coordinate.
 * @author Tobias Koch
 */
public class Coordinate {
    /**
     * Allowed difference to identify similar coordinates.
     */
    private final static double EPSILON = 1e-7;

    /**
     * Coordinates.
     */
    private double x = 0;
    private double y = 0;
    private double z = 0;

    /**
     * Constructor.
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Compute Euclidean distance to given coordinate.
     * @param coord    Coordinate to compute distance to
     * @return  distance
     */
    public double getDistance(Coordinate coord) {
        return Math.sqrt(Math.pow(this.x - coord.x, 2)
                       + Math.pow(this.y - coord.y, 2)
                       + Math.pow(this.z - coord.z, 2));
    }

    /**
     * Compare this coordinate with the given one. Does a null check.
     * Coordinates are equal if the difference is below EPSILON.
     * @param coord    Coordinate to compare to
     * @return  true if all attributes are equal
     */
    public boolean isEqual(Coordinate coord) {
        boolean isEqual = false;
        if (coord != null) {
            isEqual = Math.abs(this.x - coord.x) < Coordinate.EPSILON
                   && Math.abs(this.y - coord.y) < Coordinate.EPSILON
                   && Math.abs(this.z - coord.z) < Coordinate.EPSILON;
        }
        return isEqual;
    }

    /**
     * Will forward to isEqual if the given Object is a Coordinate.
     * @param obj   Object to compare to
     * @return  true if obj is a Coordinate and all attributes are equal
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Coordinate) {
            isEqual = this.isEqual((Coordinate)obj);
        }
        return isEqual;
    }

    /**
     * TODO: This is actually not a nice solution as we are working with a tolerance EPSILON
     * TODO: in our equals/ isEqual function. So the hashCode might be unequal although
     * TODO: equals returns true. Actually it is not a good style to override the equals
     * TODO: function when working with tolerances.
     * Create hash code based on coordinates.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }
}
