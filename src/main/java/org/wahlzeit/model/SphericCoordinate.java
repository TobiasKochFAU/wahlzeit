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

import org.wahlzeit.utils.DesignPattern;

import java.util.HashMap;

/**
 * Class representing a spherical coordinate with latitude and longitude in degree.
 * Coordinate is specified to lie on the earths surface.
 * Distances and coordinate precision is up to {@link AbstractCoordinate#DELTA}
 *
 * @author Tobias Koch
 */
@DesignPattern(
        name = "Value Object",
        participants = {"ValueObject"}
)
public class SphericCoordinate extends AbstractCoordinate {

    private static final HashMap<Integer, SphericCoordinate> instances = new HashMap<>();

    /**
     * Coordinates with precision of {@link #DELTA} in degree.
     */
    private final double latitude;
    private final double longitude;

    /**
     * Constructor. Will round latitude and longitude to {@link #DELTA} decimals.
     * Then checks whether they are valid.
     * @param latitude  latitude coordinate
     * @param longitude     longitude coordinate
     * @throws CoordinateRuntimeException     check {@link #assertClassInvariants()}
     */
    private SphericCoordinate(double latitude, double longitude) throws CoordinateRuntimeException {
        this.latitude = AbstractCoordinate.round(latitude);
        this.longitude = AbstractCoordinate.round(longitude);

        this.assertClassInvariants();
    }

    /**
     * Create/ get value object with given latitude and longitude values.
     * @param latitude  latitude coordinate
     * @param longitude     longitude coordinate
     * @return  instance of CartesianCoordinate with desired x, y, z values
     * @throws CoordinateRuntimeException   check {@link #assertClassInvariants()}
     */
    public static SphericCoordinate getInstance(double latitude, double longitude) throws CoordinateRuntimeException {
        SphericCoordinate coord = new SphericCoordinate(latitude, longitude);
        synchronized (instances) {
            if (!instances.containsValue(coord)) {
                instances.put(coord.hashCode(), coord);
            }
            return instances.get(coord.hashCode());
        }
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Creates a copy of this object.
     * @return  copy of this object.
     */
    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    /**
     * Convert this Coordinate to a CartesianCoordiante.
     * Formula: https://de.wikipedia.org/wiki/Kugelkoordinaten#Umrechnungen
     * @return  this Coordinate as CartesianCoordinate.
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        double radLat = Math.toRadians(this.latitude);
        double radLong = Math.toRadians(this.longitude);
        double rMulSinLat = SphericCoordinate.EARTH_RADIUS * Math.sin(radLat);

        double x = rMulSinLat * Math.cos(radLong);
        double y = rMulSinLat * Math.sin(radLong);
        double z = SphericCoordinate.EARTH_RADIUS * Math.cos(radLat);

        return CartesianCoordinate.getInstance(x, y, z);
    }

    /**
     * Compute spheric distance to given coordinate.
     * Distance is rounded to {@link #DELTA} decimals.
     * Formula: https://en.wikipedia.org/wiki/Great-circle_distance#Computational_formulas
     * @param coord     Coordinate to compute distance to
     * @return  spheric distance
     */
    protected double doGetSphericDistance(Coordinate coord) {
        SphericCoordinate tmp = coord.asSphericCoordinate();

        // convert to radians
        double radLat1 = Math.toRadians(this.latitude);
        double radLat2 = Math.toRadians(tmp.latitude);

        // co-/sine computation
        double cosLat1 = Math.cos(radLat1);
        double cosLat2 = Math.cos(radLat2);
        double sinLat1 = Math.sin(radLat1);
        double sinLat2 = Math.sin(radLat2);

        // delta longitude
        double deltaLong = Math.abs(Math.toRadians(this.longitude) - Math.toRadians(tmp.longitude));
        double cosDeltaLong = Math.cos(deltaLong);

        // set up numerator
        double numeratorA = Math.pow(cosLat2 * Math.sin(deltaLong), 2);
        double numeratorB = Math.pow((cosLat1 * sinLat2) - (sinLat1 * cosLat2 * cosDeltaLong), 2);
        double numerator = Math.sqrt(numeratorA + numeratorB);

        double denominator = (sinLat1 * sinLat2) + (cosLat1 * cosLat2 * cosDeltaLong);
        return AbstractCoordinate.round(SphericCoordinate.EARTH_RADIUS * Math.atan2(numerator, denominator));
    }

    /**
     * Throw exception if (this) latitude or longitude are invalid.
     * @throws CoordinateRuntimeException if (-90 > latitude > 90) or (-180 > longitude > 180)
     */
    @Override
    protected void assertClassInvariants() throws CoordinateRuntimeException {
        if (this.latitude < -90.0d || this.latitude > 90.0d || this.longitude < -180.0d || this.longitude > 180.0d) {
            throw new CoordinateRuntimeException("Invalid position!");
        }
    }
}
