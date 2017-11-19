package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    /**
     *  Member test
     */
    @Test
    public void testMember() {
        // Arrange
        CartesianCoordinate coord0 = new CartesianCoordinate(0, 1, 2);
        CartesianCoordinate coord1 = new CartesianCoordinate(0, 1, 2);
        CartesianCoordinate coord2 = new CartesianCoordinate(2, 3, 4);
        Location loc0 = new Location(coord0);
        Location loc1 = new Location(coord1);
        Location loc2 = new Location(coord2);

        // Act + assert
        // Pointer check
        assertTrue(loc0.coordinate == coord0);
        assertTrue(loc0.coordinate != coord1);
        assertTrue(loc1.coordinate == coord1);
        assertTrue(loc1.coordinate != coord0);  // always true
        assertTrue(loc2.coordinate == coord2);
        assertTrue(loc2.coordinate != coord1);

        // Member check
        assertTrue(loc0.coordinate.isEqual(coord1));
        assertEquals(loc0.coordinate, coord1);
        assertTrue(loc1.coordinate.isEqual(coord0));
        assertEquals(loc1.coordinate, coord0);
        assertFalse(loc2.coordinate.isEqual(coord0));
        assertNotEquals(loc2.coordinate, coord1);

        // Exchange member + check
        loc0.coordinate = coord2;
        loc2.coordinate = coord1;
        assertTrue(loc2.coordinate == coord1);  // always true
        assertTrue(loc2.coordinate != coord2);  // always true
        assertTrue(loc2.coordinate.isEqual(coord1));
        assertEquals(loc2.coordinate, coord1);
        assertFalse(loc1.coordinate.isEqual(coord2));
    }
}
