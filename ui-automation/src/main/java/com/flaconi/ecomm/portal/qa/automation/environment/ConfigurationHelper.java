package com.flaconi.ecomm.portal.qa.automation.environment;

import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main automation initializer, 
 * 
 * @author bharath
 *
 */


// TODO this class can be refractored to be still better
public enum ConfigurationHelper {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationHelper.class);
	private static final String ENVIRONMENT_PROPERTY = "env";
	private static final String BROWSER_PROPERTY = "browser";
	private static final String SELENIUM_SERVER_PROPERTY = "server";

	private static final String DEFAULT_SERVER_URL = "http://localhost:4444/wd/hub";

	// Error messages
	private static final String ENV_NOT_EXIST_MESSAGE = "Looks like the environment %s does not exist";
	private static final String BROWSER_NOT_SUPPORTED_MESSAGE = "The browser %s is not supported at the moment";
	private static final String INVALID_URL_FORMAT = "The given server Url %s is invalid";

	/*
	 * Enum representing a list of supported browsers, It is possible to extend
	 * the list of browsers , for code challenge this is ok !
	 */
	public enum Browser {
		FIREFOX, CHROME
	}

	/**
	 * Read the environment from the system property env and convert it to
	 * appropriate environment
	 * 
	 * @return The appropriate Environment
	 * 
	 */
	public Environment getEnvironment() {
		final String environmentString = System.getProperty(ENVIRONMENT_PROPERTY);
		Environment environment = null;
		if (environmentString == null || environmentString.isEmpty()) {
			LOGGER.warn("No environment is specified , Using prod as default ");
			environment = Environment.PROD;
		} else {
			try {
				environment = Environment.valueOf(environmentString.toUpperCase().trim());
			} catch (Exception err) {
				throw new RuntimeException(String.format(ENV_NOT_EXIST_MESSAGE, environmentString));
			}
		}
		return environment;

	}

	public Browser getSelectedBrowser() {
		final String browserString = System.getProperty(BROWSER_PROPERTY);
		Browser browser = null;
		if (browserString == null || browserString.isEmpty()) {
			LOGGER.warn("No browser given , Selecting chrome by default");
			browser = Browser.CHROME;
		} else {
			try {
				browser = Browser.valueOf(browserString.toUpperCase().trim());
			} catch (Exception err) {
				throw new RuntimeException(String.format(BROWSER_NOT_SUPPORTED_MESSAGE, browserString));
			}
		}
		return browser;
	}

	public URL getSeleniumServerUrl() {
		String serverUrlString = System.getProperty(SELENIUM_SERVER_PROPERTY);
		URL serverUrl = null;
		if (serverUrlString == null || serverUrlString.isEmpty()) {
			LOGGER.warn("No server url given , I am Assuming that you are running selenium server in standalone mode ");
			try {
				serverUrl = new URL(DEFAULT_SERVER_URL);
			} catch (MalformedURLException e) {
				// Impossible condition
				throw new RuntimeException(e);
			}
		} else {
			try {
				serverUrl = new URL(serverUrlString.trim());
			} catch (MalformedURLException e) {
				throw new RuntimeException(String.format(INVALID_URL_FORMAT, serverUrlString));
			}
		}

		return serverUrl;

	}

}
