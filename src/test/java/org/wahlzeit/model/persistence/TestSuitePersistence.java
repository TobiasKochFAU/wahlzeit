package org.wahlzeit.model.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //AbstractAdapterTest.class,    // does not work in test suite
        DatastoreAdapterTest.class
})

public class TestSuitePersistence {

}
