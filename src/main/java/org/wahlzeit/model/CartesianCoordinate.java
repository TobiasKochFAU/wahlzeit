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

import java.util.HashMap;

/**
 * Class representing a cartesian coordinate with x, y, z axis.
 * Coordinate is specified to lie on the earths surface.
 * Distances and coordinate precision is up to {@link AbstractCoordinate#DELTA}
 *
 * @author Tobias Koch
 */
public class CartesianCoordinate extends AbstractCoordinate {

    /**
     * Denotes the precision of sqrt(x^2 + y^2 + z^2) w.r.t. earth radius ({@link #EARTH_RADIUS}).
     */
    private static final double EARTH_RADIUS_DELTA = 1.5d;

    private static final HashMap<Integer, CartesianCoordinate> instances = new HashMap<>();

    /**
     * Coordinates with precision of {@link #DELTA}.
     */
    private final double x;
    private final double y;
    private final double z;

    /**
     * Constructor. Will round x, y, z to {@link #DELTA} decimal places.
     * Then checks whether x, y, z lie on the earths radius.
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @throws CoordinateRuntimeException     check {@link #assertClassInvariants()}
     */
    private CartesianCoordinate(double x, double y, double z) throws CoordinateRuntimeException {
        this.x = AbstractCoordinate.round(x);
        this.y = AbstractCoordinate.round(y);
        this.z = AbstractCoordinate.round(z);

        this.assertClassInvariants();
    }

    /**
     * Create/ get value object with given x, y, z values.
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @return  instance of CartesianCoordinate with desired x, y, z values
     * @throws CoordinateRuntimeException   check {@link #assertClassInvariants()}
     */
    public static CartesianCoordinate getInstance(double x, double y, double z) throws CoordinateRuntimeException {
        CartesianCoordinate coord = new CartesianCoordinate(x, y, z);
        synchronized (instances) {
            if (!instances.containsValue(coord)) {
                instances.put(coord.hashCode(), coord);
            }
            return instances.get(coord.hashCode());
        }
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    /**
     * Creates a copy of this object.
     * @return  copy of this object.
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
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
        return SphericCoordinate.getInstance(latitude, longitude);
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
     * Throw exception if (this) x, y, z do not lie on the earths radius. With precision of {@link #EARTH_RADIUS_DELTA}
     * @throws CoordinateRuntimeException    if sqrt(x^2 + y^2 + z^2) != ({@link #EARTH_RADIUS} +- {@link #EARTH_RADIUS_DELTA})
     */
    @Override
    protected void assertClassInvariants() throws CoordinateRuntimeException {
        double radius = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
        if (Math.abs(radius - AbstractCoordinate.EARTH_RADIUS) > CartesianCoordinate.EARTH_RADIUS_DELTA) {
            throw new CoordinateRuntimeException("Invalid axes values! Coordinate does not lie on the earths radius.");
        }
    }
}
