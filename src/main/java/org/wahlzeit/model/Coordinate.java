package org.wahlzeit.model;

/**
 * Class representing a 3d-coordinate.
 * @author Tobias Koch
 */
public class Coordinate {
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
     * Compare this coordinate with the given one.
     * @param coord    Coordinate to compare to
     * @return  true if all attributes are equal
     */
    public boolean isEqual(Coordinate coord) {
        return this.x == coord.x
            && this.y == coord.y
            && this.z == coord.z;
    }

    /**
     * Will forward to isEqual if the given Object is a Coordinate.
     * @param obj   Object to compare to
     * @return  true if obj is a Coordinate and all attributes are equal
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj != null && this.getClass() == obj.getClass()) {
            isEqual = this.isEqual((Coordinate)obj);
        }
        return isEqual;
    }
}
