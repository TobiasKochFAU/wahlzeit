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
 * Class representing a cartesian coordinate with x, y, z axis.
 * Coordinate is specified to lie on the earths surface.
 * Distances and coordinate precision is up to {@link AbstractCoordinate#DELTA}
 *
 * @author Tobias Koch
 */
public class CartesianCoordinate extends AbstractCoordinate {

    /**
     * Denotes the precision of (x + y + z) w.r.t. earth radius ({@link #EARTH_RADIUS}).
     */
    public static final double EARTH_RADIUS_DELTA = Math.pow(10, -AbstractCoordinate.DELTA);

    /**
     * Coordinates with precision of {@link #DELTA}.
     */
    protected final double x;
    protected final double y;
    protected final double z;

    /**
     * Constructor. Will round x, y, z to {@link #DELTA} decimals.
     * Then checks whether x, y, z lie on the earths radius.
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @throws IllegalArgumentException     check {@link #assertValidAxisValues(double, double, double)}
     */
    public CartesianCoordinate(double x, double y, double z) throws IllegalArgumentException {
        double tmpX = AbstractCoordinate.round(x);
        double tmpY = AbstractCoordinate.round(y);
        double tmpZ = AbstractCoordinate.round(z);

        this.assertValidAxisValues(tmpX, tmpY, tmpZ);

        this.x = tmpX;
        this.y = tmpY;
        this.z = tmpZ;
    }

    /**
     * Copy Constructor. Does a null check.
     * @param coord     Coordinate to copy.
     * @throws IllegalArgumentException     If coord is null
     */
    public CartesianCoordinate(Coordinate coord) throws IllegalArgumentException {
        this.assertIsNotNull(coord);

        CartesianCoordinate tmp;
        if(coord instanceof CartesianCoordinate) {
            tmp = (CartesianCoordinate) coord;
        }
        else {
            tmp = coord.asCartesianCoordinate();
        }

        this.x = tmp.x;
        this.y = tmp.y;
        this.z = tmp.z;
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
     * Does a null check as precondition and a distance check as postcondition.
     * @param coord     Coordinate to compute distance to
     * @return  cartesian distance
     * @throws IllegalArgumentException     if coord is null
     */
    @Override
    public double getCartesianDistance(Coordinate coord) throws IllegalArgumentException {
        this.assertIsNotNull(coord);
        double distance = this.doGetCartesianDistance(coord);
        this.assertValidDistance(distance);
        return distance;
    }

    /**
     * Compute Euclidean distance to given coordinate.
     * Distance is rounded to {@link #DELTA} decimals.
     * @param coord     Coordinate to compute distance to
     * @return  cartesian distance
     */
    protected double doGetCartesianDistance(Coordinate coord) {
        CartesianCoordinate tmp = coord.asCartesianCoordinate();
        return AbstractCoordinate.round(Math.sqrt(Math.pow(this.x - tmp.x, 2)
                                                + Math.pow(this.y - tmp.y, 2)
                                                + Math.pow(this.z - tmp.z, 2)));
    }

    /**
     * Convert this CartesianCoordinate to a SphericCoordinate
     * as that class contains the spheric distance implementation.
     * @param coord     Coordinate to compute distance to.
     * @return  spheric distance.
     */
    @Override
    public double getSphericDistance(Coordinate coord) {
        return this.asSphericCoordinate().getSphericDistance(coord);
    }

    /**
     * Compare this coordinate with the given one.
     * Does a null check. But throws no exception.
     * Coordinates are equal if all attributes are equal.
     * @param coord    Coordinate to compare to
     * @return  true if all attributes are equal
     */
    @Override
    public boolean isEqual(Coordinate coord) {
        boolean isEqual = false;
        if (coord != null) {
            CartesianCoordinate tmp = coord.asCartesianCoordinate();
            isEqual = this.x == tmp.x
                   && this.y == tmp.y
                   && this.z == tmp.z;
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

    /**
     * Throw exception if x, y, z do not lie on the earths radius. With precision of {@link #EARTH_RADIUS_DELTA}
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     * @throws IllegalStateException    if (x + y + z) != ({@link #EARTH_RADIUS} +- {@link #EARTH_RADIUS_DELTA})
     */
    protected void assertValidAxisValues(double x, double y, double z) throws IllegalArgumentException {
        if (Math.abs((x + y + z) - AbstractCoordinate.EARTH_RADIUS) <= EARTH_RADIUS_DELTA) {
            throw new IllegalArgumentException("Invalid axis values! Coordinates do not lie on the earths radius.");
        }
    }
}
