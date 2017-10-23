package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(coord0.equals(coord0));  // always true
        assertTrue(coord0.isEqual(coord1));
        assertTrue(coord0.equals(coord1));
        assertTrue(coord4.isEqual(coord4));
        assertTrue(coord5.equals(coord5));  // always true

        assertFalse(coord1.isEqual(coord2));
        assertFalse(coord1.equals(coord2));
        assertFalse(coord2.equals(5));
        assertFalse(coord2.equals(null));

        assertFalse(coord0.equals(coord5));
        assertFalse(coord0.isEqual(coord3));
        assertFalse(coord1.equals(coord3));
        assertFalse(coord1.isEqual(coord4));
        assertFalse(coord2.isEqual(coord5));
        assertFalse(coord2.equals(coord4));
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
        assertEquals(0, coord0.getDistance(coord1), coord1.getDistance(coord0));
        assertEquals(0.0, coord0.getDistance(coord1), coord1.getDistance(coord0));
        assertEquals(0.0f, coord0.getDistance(coord1), coord1.getDistance(coord0));

        assertFalse(coord1.getDistance(coord2) == 0);
        assertTrue(Math.abs(coord1.getDistance(coord2) - 3.4641016) < 1e-7);
        assertTrue(coord1.getDistance(coord2) == dist);
        assertTrue(coord3.getDistance(coord4) == 3);
    }
}
