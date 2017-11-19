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

        PowerPhoto p1 = factory.createPhoto();
        PhotoId id = new PhotoId(1337);
        PowerPhoto p2 = factory.createPhoto(id);
        PowerPhoto p3 = factory.createPhoto(id);

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
        assertEquals(p1.getClass(), p2.getClass());
        assertEquals(p2.getClass(), p3.getClass());
    }
}
