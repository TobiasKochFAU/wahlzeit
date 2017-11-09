package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class PowerPhotoFactoryTest {

    @ClassRule
    public static TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());

    @Test()
    public void testFactory() {
        PowerPhotoFactory factory = PowerPhotoFactory.getInstance();

        assertNotNull(factory);

        PowerPhoto p1 = factory.createPowerPhoto("plant1", "beer", 2012, 5.2d, 2017);
        PhotoId id = new PhotoId(1337);
        PowerPhoto p2 = factory.createPowerPhoto(id, "plant2", "beer", 2014, 2.5d, 2017);
        PowerPhoto p3 = factory.createPowerPhoto(id, "plant3", "wine", 2014, 42.42d, 2042);

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
        assertEquals(p1.getClass(), p2.getClass());
        assertEquals(p2.getClass(), p3.getClass());
    }
}
