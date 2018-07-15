package com.flaconi.ecomm.portal.qa.automation.test.base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;
import com.flaconi.ecomm.portal.qa.automation.bot.WebDriverBot;
import com.flaconi.ecomm.portal.qa.automation.environment.ConfigurationHelper;
import com.flaconi.ecomm.portal.qa.automation.environment.Environment;
import com.flaconi.ecomm.portal.qa.automation.environment.EnvironmentHandler;

/**
 * 
 * This is the base test class that all the other tests should inherit , usually
 * contains initialization code for the tests, pre-srequisite data setup etc.
 * For example maybe you need to access a web service to create data ? you
 * should do it here.
 * 
 * NOTE : There is a reason that the initialization code is not put in
 * constructor . testNG calls the constructor at once during the test case
 * initialization . For example if we have 100 + test classes , All driver
 * instances will be initialized all at once and the application will crash!
 * That is why the initialization code is put under the Setup method
 * 
 * @author bharath
 *
 */
public abstract class BaseTest {
	private String homePageUrl;
	private Bot botInstance;

	/**
	 * Setup method . This is declared as final so that all the deriving test
	 * classes will not be able to override it and mess up the initialization
	 * code !
	 */
	@BeforeClass(alwaysRun = true)
	public final void setup() {
		final Environment environment = ConfigurationHelper.INSTANCE.getEnvironment();
		this.homePageUrl = EnvironmentHandler.INSTANCE.getWebUrlFor(environment);
		this.botInstance = WebDriverBot.newInstance();
	}

	public String getHomePageUrl() {
		return this.homePageUrl;
	}

	public Bot getBotInstance() {
		return this.botInstance;
	}

	@AfterClass(alwaysRun = true)
	public void quit() {
		if (this.botInstance != null) {
			this.botInstance.quit();
		}
	}

}
