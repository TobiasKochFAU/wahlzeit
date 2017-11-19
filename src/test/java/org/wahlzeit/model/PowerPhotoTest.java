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
        PowerPhoto p1 = new PowerPhoto();
        PhotoId id = new PhotoId(1337);
        PowerPhoto p2 = new PowerPhoto(id);
        PowerPhoto p3 = new PowerPhoto(id);

        p1.setName("plant1");
        p1.setType("beer");
        p1.setYearStartUp(2012);
        p1.setCapacitySinceStart(5.2d, 2017);

        p2.setName("plant2");
        p2.setType("beer");
        p2.setYearStartUp(2014);
        p2.setCapacitySinceStart(2.5d, 2017);

        p3.setName("plant3");
        p3.setType("wine");
        p3.setYearStartUp(2014);
        p3.setCapacitySinceStart(42.42d, 2042);

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
        PowerPhoto p1 = new PowerPhoto();

        p1.setName("plant1");
        p1.setType("beer");
        p1.setYearStartUp(2012);
        p1.setCapacitySinceStart(5.2d, 2017);

        p1.setCapacitySinceStart(7.3, 2018);
        expectedException.expect(IllegalArgumentException.class);
        p1.setCapacitySinceStart(2.4, 2011);
    }
}
