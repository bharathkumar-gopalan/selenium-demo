package com.flaconi.ecomm.portal.qa.automation.environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple environment handler . Reads and loads the environment URL from the
 * environment base folder path located at src/main/resources/environment/
 * folder.
 * 
 * At this moment there is only one web url , It is possible to add multiple
 * endpoints for example API Url or some service url etc
 * 
 * @author bharath
 *
 */
public enum EnvironmentHandler {
	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentHandler.class);
	private static final String ENVIRONMENT_FOLDER_PATH_PATTERN = "environments/%s/endpoint.properties";
	private static final String WEB_URL_PROPERTY = "web-url";

	// Exception messages
	private static final String FILE_NOT_FOUND_MESSAGE = "Could not find the environment file @ %s, Are you sure that the correct environment is seleceted?";
	private static final String FILE_EMPTY_MESSAGE = "The file %s is empty , Need to know the enviornment constants to load!";
	private static final String GENERIC_FAILURE = "An error has occurred while reading the property file , The message was %s";

	/**
	 * Load the specific environment file
	 * 
	 * @param environment
	 *            The environment for which to load the file
	 * @return Property representing the environment constants
	 */
	private Properties readPropsForEnvironment(final Environment environment) {
		LOGGER.debug("Attempting to load the property file for the environment {}", environment);
		final String folderPath = String.format(ENVIRONMENT_FOLDER_PATH_PATTERN, environment.toString());
		final InputStream is = EnvironmentHandler.class.getClassLoader().getResourceAsStream(folderPath);
		if (is == null) {
			throw new RuntimeException(String.format(FILE_NOT_FOUND_MESSAGE, folderPath));
		}
		final Properties props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException(String.format(GENERIC_FAILURE, e.getMessage()));
		}
		// Check if the file is not empty
		if (props.isEmpty()) {
			throw new RuntimeException(String.format(FILE_EMPTY_MESSAGE, folderPath));
		}
		LOGGER.debug("Loaded the file @ {}", folderPath);
		return props;
	}

	/**
	 * Get the Web url for the given environment
	 * 
	 * @param environment
	 *            The environment for which to load the file
	 * @return String containing the Web url
	 */
	public String getWebUrlFor(Environment environment) {
		String url = null;
		switch (environment) {
		case PROD:
			// I know that every invocation reads the file , Its ok for the demo
			// . I can cache this later !
			url = this.readPropsForEnvironment(environment).getProperty(WEB_URL_PROPERTY);
			break;
		}
		if (url == null || url.isEmpty()) {
			throw new RuntimeException("");
		}
		LOGGER.debug("The returned url is {}", url);
		return url;
	}


}
