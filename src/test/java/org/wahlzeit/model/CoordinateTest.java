package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {
    /**
     *  Equality tests
     */
    @Test
    public void testEquals() {
        // Arrange
        Coordinate coord0 = new Coordinate(0, 1, 2);
        Coordinate coord1 = new Coordinate(0, 1, 2);
        Coordinate coord2 = new Coordinate(2, 3, 4);
        Coordinate coord3 = new Coordinate(5.0, 5.1, 5.2);
        Coordinate coord4 = new Coordinate(5.0, 5.1, 5.2);
        Coordinate coord5 = new Coordinate(2.3, 3.4, 4.5);

        // Act + assert
        assertTrue(coord0.isEqual(coord0));
        assertEquals(coord0, coord0);
        assertEquals(coord0.hashCode(), coord0.hashCode());
        assertTrue(coord0.isEqual(coord1));
        assertEquals(coord0, coord1);
        assertEquals(coord0.hashCode(), coord1.hashCode());
        assertTrue(coord4.isEqual(coord4));
        assertEquals(coord5, coord5);

        assertFalse(coord1.isEqual(null));
        assertFalse(coord1.isEqual(coord2));
        assertNotEquals(coord1, coord2);
        assertNotEquals(coord1.hashCode(), coord2.hashCode());
        assertNotEquals(coord2, 5);
        assertNotEquals(coord2, null);

        assertNotEquals(coord0, coord5);
        assertNotEquals(coord0.hashCode(), coord5.hashCode());
        assertFalse(coord0.isEqual(coord3));
        assertNotEquals(coord1, coord3);
        assertNotEquals(coord1.hashCode(), coord3.hashCode());
        assertFalse(coord1.isEqual(coord4));
        assertFalse(coord2.isEqual(coord5));
        assertNotEquals(coord2, coord4);
        assertNotEquals(coord2.hashCode(), coord4.hashCode());
    }

    /**
     *  Distance computation tests
     */
    @Test
    public void testDistance() {
        // Arrange
        Coordinate coord0 = new Coordinate(0, 1, 2);
        Coordinate coord1 = new Coordinate(0, 1, 2);
        Coordinate coord2 = new Coordinate(2, 3, 4);
        Coordinate coord3 = new Coordinate(4, 1, -2);
        Coordinate coord4 = new Coordinate(2, 3, -1);
        double dist = Math.sqrt(Math.pow(0 - 2, 2)
                              + Math.pow(1 - 3, 2)
                              + Math.pow(2 - 4, 2));

        // Act + Assert
        assertEquals(coord0.getDistance(coord1), 0, 1e-7);
        assertEquals(coord1.getDistance(coord0), 0, 1e-7);
        assertEquals(coord0.getDistance(coord1), coord1.getDistance(coord0), 1e-7);

        assertNotEquals(coord1.getDistance(coord2), 0);
        assertEquals(coord1.getDistance(coord2), 3.4641016, 1e-7);
        assertEquals(coord2.getDistance(coord1), dist, 1e-7);
        assertEquals(coord3.getDistance(coord4), 3, 1e-7);
    }
}
