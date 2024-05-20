package com.vko.labworkproducer;

import com.vko.labworkproducer.chrome.ChromeTestStarter;
import com.vko.labworkproducer.firefox.FirefoxTestStarter;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ChromeTestStarter.class, FirefoxTestStarter.class})
public class AllTestStarter {
}
