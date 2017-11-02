package org.wahlzeit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.handlers.TestSuiteHandlers;
import org.wahlzeit.model.TestSuiteModel;
import org.wahlzeit.services.TestSuiteServices;
import org.wahlzeit.utils.TestSuiteUtils;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestSuiteHandlers.class,
        TestSuiteModel.class,
        TestSuiteServices.class,
        TestSuiteUtils.class
})

public class TestSuiteWahlzeit {

}
