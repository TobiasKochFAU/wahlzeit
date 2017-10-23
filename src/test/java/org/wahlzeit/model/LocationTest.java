package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LocationTest {

    /**
     *  Member test
     */
    @Test
    public void testMember() {
        // Arrange
        Coordinate coord0 = new Coordinate(0, 1, 2);
        Coordinate coord1 = new Coordinate(0, 1, 2);
        Coordinate coord2 = new Coordinate(2, 3, 4);
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
        assertTrue(loc0.coordinate.equals(coord1));
        assertTrue(loc1.coordinate.isEqual(coord0));
        assertTrue(loc1.coordinate.equals(coord0));
        assertFalse(loc2.coordinate.isEqual(coord0));
        assertFalse(loc2.coordinate.equals(coord1));

        // Exchange member + check
        loc0.coordinate = coord2;
        loc2.coordinate = coord1;
        assertTrue(loc2.coordinate == coord1);  // always true
        assertTrue(loc2.coordinate != coord2);  // always true
        assertTrue(loc2.coordinate.isEqual(coord1));
        assertTrue(loc2.coordinate.equals(coord1));
        assertFalse(loc1.coordinate.isEqual(coord2));
    }
}
