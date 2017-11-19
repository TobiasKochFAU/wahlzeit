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

public class SphericCoordinate implements Coordinate {

    /**
     * Radius in kilometer. Assume we are only working with spherical coordinates on earth
     * we can set a constant radius. We ignore the fact that the earth is not a perfect spherical.
     */
    public static final double EARTH_RADIUS = 6371.0d;

    /**
     * Degrees (well maybe it is better to keep them in
     * radians due to the conversion computations).
     */
    private final double latitude;
    private final double longitude;

    /**
     * Constructor.
     * @param latitude  latitude coordinate
     * @param longitude     longitude coordinate
     */
    public SphericCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Copy constructor.
     * @param coord     Coordinate to copy.
     */
    public SphericCoordinate(Coordinate coord) {
        SphericCoordinate tmp;
        if(coord instanceof CartesianCoordinate) {
            tmp = coord.asSphericCoordinate();
        }
        else {
            tmp = (SphericCoordinate) coord;
        }
        this.latitude = tmp.latitude;
        this.longitude = tmp.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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
        // temporary
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
     * Formula: https://en.wikipedia.org/wiki/Great-circle_distance#Computational_formulas
     * @param coord    Coordinate to compute distance to
     * @return  spheric distance
     */
    @Override
    public double getDistance(Coordinate coord) {
        SphericCoordinate tmp;
        if (coord instanceof CartesianCoordinate) {
            tmp = coord.asSphericCoordinate();
        }
        else {
            tmp = (SphericCoordinate) coord;
        }

        // convert to radians
        double radLat1 = Math.toRadians(this.latitude);
        double radLong1 = Math.toRadians(this.longitude);
        double radLat2 = Math.toRadians(tmp.latitude);
        double radLong2 = Math.toRadians(tmp.longitude);

        // co-/sine computation
        double cosLat1 = Math.cos(radLat1);
        double cosLat2 = Math.cos(radLat2);
        double sinLat1 = Math.sin(radLat1);
        double sinLat2 = Math.sin(radLat2);

        // delta longitude
        double deltaLong = Math.abs(radLong1 - radLong2);
        double cosDeltaLong = Math.cos(deltaLong);

        // set up numerator
        double numeratorA = Math.pow(cosLat2 * Math.sin(deltaLong), 2);
        double numeratorB = Math.pow((cosLat1 * sinLat2) - (sinLat1 * cosLat2 * cosDeltaLong), 2);
        double numerator = Math.sqrt(numeratorA + numeratorB);

        double denominator = (sinLat1 * sinLat2) + (cosLat1 * cosLat2 * cosDeltaLong);
        return SphericCoordinate.EARTH_RADIUS * Math.atan2(numerator, denominator);
    }

    /**
     * Forward to getDistance as that computes the spheric distance.
     * @param coord     Coordinate to compute distance to
     * @return  spheric distance
     */
    @Override
    public double getSphericDistance(Coordinate coord) {
        return this.getDistance(coord);
    }

    /**
     * CartesianCoordinate does compute the cartesian distance in the
     * getDistance() function.
     * @param coord     Coordinate to compute distance to.
     * @return  cartesian distance.
     */
    @Override
    public double getCartesianDistance(Coordinate coord) {
        return this.asCartesianCoordinate().getDistance(coord);
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
        SphericCoordinate tmp;
        if (coord instanceof CartesianCoordinate) {
            tmp = coord.asSphericCoordinate();
        }
        else {
            tmp = (SphericCoordinate) coord;
        }

        // do null check
        if (tmp != null) {
            isEqual = this.latitude == tmp.latitude
                   && this.longitude == tmp.longitude;
        }
        return isEqual;
    }

    /**
     * Will forward to isEqual if the given Object is a Coordinate.
     * isEqual will do a conversion to SphericCoordinate if necessary.
     * @param obj   Object to compare to
     * @return  true if obj is convertible to SphericCoordinate and all attributes are equal
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
        return Objects.hash(this.latitude, this.longitude);
    }
}
