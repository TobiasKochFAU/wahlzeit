package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.wahlzeit.testEnvironmentProvider.*;

public class PowerManagerTest {

    @ClassRule
    public static TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());

    /**
     * Test get behavior of the manager
     */
    @Test()
    public void testCreatePowers() {
        PowerManager manager = PowerManager.getInstace();
        assertNotNull(manager);

        Power p1 = manager.createPower("banana1", "banana");
        Power p2 = manager.createPower("banana2", "banana");
        Power p3 = manager.createPower("potato", "potato");
        Power p4 = manager.createPower("banana2", "banana");

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
        assertNotNull(p4);

        assertNotEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p2, p4);

        assertEquals(p1.getPowerType(), p2.getPowerType());
        assertEquals(p1.getPowerType(), p4.getPowerType());
        assertNotEquals(p2.getPowerType(), p3.getPowerType());
    }
}
