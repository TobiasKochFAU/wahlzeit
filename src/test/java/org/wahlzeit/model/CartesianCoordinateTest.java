package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CartesianCoordinateTest {

    double thirdRadius = 0;
    CartesianCoordinate coord0 = null;
    CartesianCoordinate coord1 = null;
    CartesianCoordinate coord2 = null;
    CartesianCoordinate coord3 = null;
    CartesianCoordinate coord4 = null;

    @Before
    public void arrange() {
        thirdRadius = AbstractCoordinate.EARTH_RADIUS / 3.0d;
        coord0 = new CartesianCoordinate(thirdRadius, thirdRadius, thirdRadius);
        coord1 = new CartesianCoordinate(thirdRadius, thirdRadius, thirdRadius);
        coord2 = new CartesianCoordinate(thirdRadius + 1, thirdRadius - 1, thirdRadius);
        coord3 = new CartesianCoordinate(thirdRadius + 5.0, thirdRadius - 2, thirdRadius - 3);
        coord4 = new CartesianCoordinate(thirdRadius - 100, thirdRadius + 50, thirdRadius + 50);
    }

    /**
     *  Equality tests
     */
    @Test
    public void testEquals() {
        // Act + assert
        assertTrue(coord0.isEqual(coord0));
        assertEquals(coord0, coord0);
        assertEquals(coord0.hashCode(), coord0.hashCode());
        assertTrue(coord0.isEqual(coord1));
        assertEquals(coord0, coord1);
        assertEquals(coord0.hashCode(), coord1.hashCode());
        assertTrue(coord3.isEqual(coord3));
        assertEquals(coord4, coord4);

        assertFalse(coord1.isEqual(null));
        assertFalse(coord1.isEqual(coord2));
        assertNotEquals(coord1, coord2);
        assertNotEquals(coord1.hashCode(), coord2.hashCode());
        assertNotEquals(coord2, 5);
        assertNotEquals(coord2, null);

        assertNotEquals(coord0, coord4);
        assertNotEquals(coord0.hashCode(), coord4.hashCode());
        assertFalse(coord0.isEqual(coord3));
        assertNotEquals(coord1, coord3);
        assertNotEquals(coord1.hashCode(), coord3.hashCode());
        assertFalse(coord1.isEqual(coord3));
        assertFalse(coord2.isEqual(coord4));
        assertNotEquals(coord2, coord3);
        assertNotEquals(coord2.hashCode(), coord3.hashCode());
    }

    /**
     *  Distance computation tests
     */
    @Test
    public void testDistance() {
        double dist = Math.sqrt(Math.pow(thirdRadius - thirdRadius + 1, 2)
                + Math.pow(thirdRadius - thirdRadius - 1, 2)
                + Math.pow(thirdRadius - thirdRadius, 2));

        // Act + Assert
        assertEquals(coord0.getDistance(coord1), 0, 1e-7);
        assertEquals(coord1.getDistance(coord0), 0, 1e-7);
        assertEquals(coord0.getDistance(coord1), coord1.getDistance(coord0), 1e-7);

        assertNotEquals(coord1.getDistance(coord2), 0);
        assertEquals(coord2.getDistance(coord1), dist, 1e-7);

        assertEquals(coord0.getCartesianDistance(coord1), 0, 1e-7);
        assertEquals(coord1.getCartesianDistance(coord0), 0, 1e-7);
        assertEquals(coord0.getCartesianDistance(coord1), coord1.getCartesianDistance(coord0), 1e-7);

        assertNotEquals(coord1.getCartesianDistance(coord2), 0);
        assertEquals(coord2.getCartesianDistance(coord1), dist, 1e-7);
    }
}
