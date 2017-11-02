package org.wahlzeit.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.wahlzeit.services.mailing.TestSuiteEmailService;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EmailAddressTest.class,
        LogBuilderTest.class,
        TestSuiteEmailService.class
})

public class TestSuiteServices {

}
