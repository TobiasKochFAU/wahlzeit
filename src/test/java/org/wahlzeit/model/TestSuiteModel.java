package org.wahlzeit.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.model.persistence.TestSuitePersistence;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestSuitePersistence.class,
        AccessRightsTest.class,
        CartesianCoordinateTest.class,
        SphericCoordinateTest.class,
        FlagReasonTest.class,
        GenderTest.class,
        GuestTest.class,
        LocationTest.class,
        PhotoFilterTest.class,
        PowerManagerTest.class,
        PowerPhotoFactoryTest.class,
        PowerPhotoManagerTest.class,
        PowerPhotoTest.class,
        TagsTest.class,
        UserStatusTest.class,
        ValueTest.class
})

public class TestSuiteModel {

}
