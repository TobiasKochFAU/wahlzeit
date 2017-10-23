package org.wahlzeit.model;

/**
 * Class representing a location.
 * @author Tobias Koch
 */
public class Location {
    /**
     * Coordinate
     */
    public Coordinate coordinate = null;

    /**
     * Constructor
     * @param coord    Coordinate
     */
    public Location(Coordinate coord) {
        this.coordinate = coord;
    }
}
