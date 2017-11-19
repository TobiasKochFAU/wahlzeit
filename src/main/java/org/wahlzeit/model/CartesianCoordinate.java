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
public class CartesianCoordinate implements Coordinate {

    /**
     * Coordinates.
     */
    private final double x;
    private final double y;
    private final double z;

    /**
     * Constructor.
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Copy Constructor.
     * @param coord     Coordinate to copy.
     */
    public CartesianCoordinate(Coordinate coord) {
        CartesianCoordinate tmp;
        if(coord instanceof SphericCoordinate) {
            tmp = coord.asCartesianCoordinate();
        }
        else {
            tmp = (CartesianCoordinate) coord;
        }
        this.x = tmp.x;
        this.y = tmp.y;
        this.z = tmp.z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    /**
     * Creates a copy of this object.
     * @return  copy of this object.
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(this);
    }

    /**
     * Convert this Coordinate to a SphericCoordiante.
     * Formula: https://de.wikipedia.org/wiki/Kugelkoordinaten#Umrechnungen
     * @return  this Coordinate as SphericCoordinate.
     */
    @Override
    public SphericCoordinate asSphericCoordinate() {
        double latitude = Math.toDegrees(Math.acos(this.z / SphericCoordinate.EARTH_RADIUS));
        double longitude = Math.toDegrees(Math.atan2(this.y, this.x));
        return new SphericCoordinate(latitude, longitude);
    }

    /**
     * Compute Euclidean distance to given coordinate.
     * @param coord    Coordinate to compute distance to
     * @return  cartesian distance
     */
    @Override
    public double getDistance(Coordinate coord) {
        CartesianCoordinate tmp;
        if (coord instanceof SphericCoordinate) {
            tmp = coord.asCartesianCoordinate();
        }
        else {
            tmp = (CartesianCoordinate) coord;
        }
        return Math.sqrt(Math.pow(this.x - tmp.x, 2)
                       + Math.pow(this.y - tmp.y, 2)
                       + Math.pow(this.z - tmp.z, 2));
    }

    /**
     * Forward to getDistance as that computes the cartesian distance.
     * @param coord     Coordinate to compute distance to
     * @return  cartesian distance
     */
    @Override
    public double getCartesianDistance(Coordinate coord) {
        return this.getDistance(coord);
    }

    /**
     * SphericCoordinate does compute the spheric distance in the
     * getDistance() function.
     * @param coord     Coordinate to compute distance to.
     * @return  spheric distance.
     */
    @Override
    public double getSphericDistance(Coordinate coord) {
        return this.asSphericCoordinate().getDistance(coord);
    }

    /**
     * Compare this coordinate with the given one. Does a null check.
     * Coordinates are equal if all attributes are equal.
     * @param coord    Coordinate to compare to
     * @return  true if all attributes are equal
     */
    @Override
    public boolean isEqual(Coordinate coord) {
        boolean isEqual = false;

        // check coordinate type, do conversion if necessary
        CartesianCoordinate tmp;
        if (coord instanceof SphericCoordinate) {
            tmp = coord.asCartesianCoordinate();
        }
        else {
            tmp = (CartesianCoordinate) coord;
        }

        // do null check
        if (tmp != null) {
            isEqual = this.x == tmp.x
                   && this.y == tmp.y
                   && this.z == tmp.z;
        }
        return isEqual;
    }

    /**
     * Will forward to isEqual if the given Object is a Coordinate.
     * isEqual will do a conversion to CartesianCoordinate if necessary.
     * @param obj   Object to compare to
     * @return  true if obj is convertible to CartesianCoordinate and all attributes are equal
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
     * Create hash code based on attributes.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }
}
