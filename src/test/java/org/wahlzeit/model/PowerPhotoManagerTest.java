package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.wahlzeit.testEnvironmentProvider.*;

import java.io.IOException;

public class PowerPhotoManagerTest {

    @ClassRule
    public static TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    /**
     * Test add behavior of the manager including exception throw
     */
    @Test()
    public void testAddPhotos() {
        PowerPhoto p1 = new PowerPhoto();
        PhotoId id = new PhotoId(1337);
        PowerPhoto p2 = new PowerPhoto(id);
        PowerPhoto p3 = new PowerPhoto(id);
        PhotoManager manager = PowerPhotoManager.getInstance();

        assertNotNull(manager);

        try {
            manager.addPhoto(p1);
            manager.addPhoto(p2);
        } catch (IOException e) {
            assertNull(e);
        }
        assertTrue(manager.hasPhoto(p1.id));
        assertTrue(manager.hasPhoto(p2.id));
        assertNotNull(manager.getPhoto(p1.id));
        assertNotNull(manager.getPhoto(p2.id));

        try {
            expectedException.expect(IllegalStateException.class);
            manager.addPhoto(p3);
        } catch (IOException e) {
            assertNotNull(e);
        }
        assertFalse(manager.hasPhoto(p3.id));
        assertNull(manager.getPhoto(p3.id));
    }
}
