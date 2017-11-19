package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SphericCoordinateTest {
    /**
     *  Equality tests
     */
    @Test
    public void testEquals() {
        // Arrange
        SphericCoordinate coord0 = new SphericCoordinate(0, 1);
        SphericCoordinate coord1 = new SphericCoordinate(0, 1);
        SphericCoordinate coord2 = new SphericCoordinate(2, 3);
        SphericCoordinate coord3 = new SphericCoordinate(5.0, 5.1);
        SphericCoordinate coord4 = new SphericCoordinate(5.0, 5.1);
        SphericCoordinate coord5 = new SphericCoordinate(2.3, 3.4);

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
        SphericCoordinate rüsselBahn = new SphericCoordinate(49.9917, 8.41321);
        SphericCoordinate rüsselOpel = new SphericCoordinate(50.0049, 8.42182);
        SphericCoordinate berlin = new SphericCoordinate(52.5164, 13.3777);
        SphericCoordinate lissabon = new SphericCoordinate(38.692668, -9.177944);

        assertEquals(rüsselBahn.getDistance(rüsselOpel), 1.593, 1e-2);
        assertEquals(berlin.getDistance(lissabon), 2317.722, 3);
        assertEquals(berlin.getDistance(berlin), 0.0, 0.0);
    }

    /**
     *  Conversion tests
     */
    @Test
    public void testConversion() {
        SphericCoordinate rüsselBahn = new SphericCoordinate(49.9917, 8.41321);
        CartesianCoordinate cart = rüsselBahn.asCartesianCoordinate();
        SphericCoordinate spheric = cart.asSphericCoordinate();

        assertEquals(rüsselBahn.getLatitude(), spheric.getLatitude(), 1e-9);
        assertEquals(rüsselBahn.getLongitude(), spheric.getLongitude(), 1e-9);
    }
}
