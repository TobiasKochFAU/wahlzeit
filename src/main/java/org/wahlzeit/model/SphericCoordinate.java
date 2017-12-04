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
 * Class representing a spherical coordinate with latitude and longitude in degree.
 * Coordinate is specified to lie on the earths surface.
 * Distances and coordinate precision is up to {@link AbstractCoordinate#DELTA}
 *
 * @author Tobias Koch
 */
public class SphericCoordinate extends AbstractCoordinate {

    /**
     * Coordinates with precision of {@link #DELTA} in degree.
     */
    protected final double latitude;
    protected final double longitude;

    /**
     * Constructor. Will round latitude and longitude to {@link #DELTA} decimals.
     * Then checks whether they are valid.
     * @param latitude  latitude coordinate
     * @param longitude     longitude coordinate
     * @throws IllegalStateException     check {@link #assertClassInvariants()}
     */
    public SphericCoordinate(double latitude, double longitude) throws IllegalStateException {
        this.latitude = AbstractCoordinate.round(latitude);
        this.longitude = AbstractCoordinate.round(longitude);

        this.assertClassInvariants();
    }

    /**
     * Copy constructor. Does a null check.
     * @param coord     Coordinate to copy.
     * @throws IllegalArgumentException     If coord is null
     */
    public SphericCoordinate(Coordinate coord) throws IllegalArgumentException {
        this.assertIsNotNull(coord);

        SphericCoordinate tmp;
        if(coord instanceof SphericCoordinate) {
            tmp = (SphericCoordinate) coord;
        }
        else {
            tmp = coord.asSphericCoordinate();
        }

        this.latitude = tmp.latitude;
        this.longitude = tmp.longitude;
    }

    /**
     * Creates a copy of this object.
     * @return  copy of this object.
     */
    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(this);
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

        return new CartesianCoordinate(x, y, z);
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
            SphericCoordinate tmp = coord.asSphericCoordinate();
            isEqual = this.latitude == tmp.latitude
                   && this.longitude == tmp.longitude;
        }
        return isEqual;
    }

    /**
     * Create hash code based on attributes.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.latitude, this.longitude);
    }

    /**
     * Throw exception if (this) latitude or longitude are invalid.
     * @throws IllegalStateException if (-90 > latitude > 90) or (-180 > longitude > 180)
     */
    private void assertClassInvariants() throws IllegalStateException {
        if (this.latitude < -90.0d || this.latitude > 90.0d || this.longitude < -180.0d || this.longitude > 180.0d) {
            throw new IllegalStateException("Invalid position!");
        }
    }
}
