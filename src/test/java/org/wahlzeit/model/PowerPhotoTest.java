package org.wahlzeit.model;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

import static org.junit.Assert.*;

public class PowerPhotoTest {

    @ClassRule
    public static TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    /**
     *
     */
    @Test
    public void testAttributes() {
        PowerPhoto p1 = new PowerPhoto("plant1", "beer", 2012, 5.2d, 2017);
        PhotoId id = new PhotoId(1337);
        PowerPhoto p2 = new PowerPhoto(id, "plant2", "beer", 2014, 2.5d, 2017);
        PowerPhoto p3 = new PowerPhoto(id, "plant3", "wine", 2014, 42.42d, 2042);

        assertNotEquals(p1.getId(), p2.getId());
        assertEquals(p1.getName(), "plant1");
        assertNotEquals(p1.getName(), p2.getName());
        assertEquals(p1.getType(), p2.getType());
        assertEquals(p1.getYearStartUp(), 2012);
        assertEquals(p1.getCapacitySinceStart(), 5.2d, 1e-7);
        assertEquals(p1.getYearCapacity(), 2017);

        assertEquals(p2.getId(), p3.getId());
        assertNotEquals(p2.getName(), p3.getName());
        assertNotEquals(p2.getType(), p3.getType());
        assertEquals(p2.getYearStartUp(), p3.getYearStartUp());
        assertNotEquals(p2.getCapacitySinceStart(), p3.getCapacitySinceStart(), 1e-7);
        assertNotEquals(p2.getYearCapacity(), p3.getYearCapacity());
    }

    /**
     *  Test exception throw in setCapacitySinceStart(...).
     */
    @Test
    public void testException() {
        PowerPhoto p1 = new PowerPhoto("plant1", "beer", 2012, 5.2d, 2017);

        p1.setCapacitySinceStart(7.3, 2018);
        expectedException.expect(IllegalArgumentException.class);
        p1.setCapacitySinceStart(2.4, 2011);
    }
}
