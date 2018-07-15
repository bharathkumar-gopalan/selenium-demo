package com.flaconi.ecomm.portal.qa.automation.bot;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flaconi.ecomm.portal.qa.automation.environment.ConfigurationHelper;
import com.flaconi.ecomm.portal.qa.automation.environment.ConfigurationHelper.Browser;

/**
 * A selenium webdriver implementation of the bot. Please do note that this bot
 * is INCOMPLETE and I have implemented the methods that are just needed for the
 * POC
 * 
 * @author bharath
 *
 */
public class WebDriverBot implements Bot {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverBot.class);
	// The page load timeout
	private static final int PAGE_LOAD_TIMEOUT_IN_SECONDS = 120;
	// Element location timeout
	private static final int ELEMENT_TIMEOUT_IN_SECONDS = 20;
	// poll interval in millis
	private static final int POLL_INTERVAL_IN_MILLIS = 500;
	private static final String ELEMENT_DELIMITER = ";";
	private final WebDriver driver;

	// Error messages
	// TODO - Need to move the error messages to a separate file
	private static final String INVALID_LOCATOR_MESSAGE = "The element locator %s has invalid locator , Element locator should be of the format locationStrtegy;locator";
	private static final String INVALID_STRATEGY_MESSAGE = "invalid locator %s - only id, name, class, css, xpath and linkText allowed";

	private static final String NO_MATCHING_ELEMENT = "Unable to find any element (with locator %s) containing text %s";
	private static final String ELEMENT_PRESENCE_TIMEOUT_MESSAGE = "Timed out waiting for the element %s to be present and visible";

	/**
	 * Create a webdriver instance based on the browser passed parameter. Please
	 * note that the configuration option is simple(There is no browser specific
	 * handling of config options . For example It is possible to configure ,
	 * the chrome browser with chrome-switches. I have just left detailed
	 * browser specific config out since this is a simple POC
	 * 
	 * @return The webdriver instance initialized
	 * 
	 */
	private WebDriver createDriverInstance() {
		final Browser browser = ConfigurationHelper.INSTANCE.getSelectedBrowser();
		final URL serverUrl = ConfigurationHelper.INSTANCE.getSeleniumServerUrl();
		DesiredCapabilities caps = null;
		LOGGER.debug("The selected browser is {} and the server url is {}", browser, serverUrl);
		switch (browser) {
		case CHROME:
			caps = DesiredCapabilities.chrome();
			break;
		case FIREFOX:
			caps = DesiredCapabilities.firefox();
			break;
		}

		return new RemoteWebDriver(serverUrl, caps);
	}

	private By getByLocator(final PageElement element) {
		final String elementLocator = element.getElementLocator();
		final String[] splitElement = elementLocator.split(ELEMENT_DELIMITER);
		By byLocator = null;
		if (splitElement.length != 2) {
			throw new RuntimeException(String.format(INVALID_LOCATOR_MESSAGE, element));
		}
		final String locationStrategy = splitElement[0];
		final String locator = splitElement[1];

		if (locationStrategy.equalsIgnoreCase("id")) {
			byLocator = By.id(locator);
		} else if (locationStrategy.equalsIgnoreCase("name")) {
			byLocator = By.name(locator);
		} else if (locationStrategy.equalsIgnoreCase("xpath")) {
			byLocator = By.xpath(locator);
		} else if (locationStrategy.equalsIgnoreCase("class")) {
			byLocator = By.className(locator);
		} else if (locationStrategy.equalsIgnoreCase("css")) {
			byLocator = By.cssSelector(locator);
		} else if (locationStrategy.equalsIgnoreCase("linkText")) {
			byLocator = By.linkText(locator);
		} else {
			throw new RuntimeException(String.format(INVALID_STRATEGY_MESSAGE, element));
		}
		return byLocator;
	}

	private WebElement waitForElementToBePresentAndVisible(PageElement element, int timeoutInSeconds) {
		final By selector = this.getByLocator(element);
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
				.withTimeout(Duration.ofSeconds(timeoutInSeconds)).ignoring(RuntimeException.class)
				.withMessage(String.format(ELEMENT_PRESENCE_TIMEOUT_MESSAGE, element))
				.pollingEvery(Duration.ofMillis(POLL_INTERVAL_IN_MILLIS));
		final WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
		return webElement;
	}

	private WebElement waitForElementToBePresentAndVisible(final PageElement element) {
		return this.waitForElementToBePresentAndVisible(element, ELEMENT_TIMEOUT_IN_SECONDS);
	}

	private WebDriverBot() {
		LOGGER.debug("Initializing the selenium browser bot ....");
		this.driver = this.createDriverInstance();
		this.driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
	}

	public void goToUrl(String url) {
		LOGGER.debug("Navigating to the URL {}", url);
		this.driver.get(url);
	}

	public void click(PageElement element) {
		LOGGER.debug("Attempting to click on the element {}", element);
		final WebElement webElement = this.waitForElementToBePresentAndVisible(element);
		webElement.click();
		LOGGER.debug("Clicked on the element {}", element);
	}

	public void type(PageElement element, String text) {
		LOGGER.debug("Sending the text {} to the element {}", element, text);
		final WebElement webElement = this.waitForElementToBePresentAndVisible(element);
		webElement.sendKeys(text);
	}

	public String getText(PageElement element) {
		LOGGER.debug("Attempting to get the text of the element {}", element);
		final WebElement webElement = this.waitForElementToBePresentAndVisible(element);
		final String text = webElement.getText();
		LOGGER.debug("The located text is {}", text);
		return text;
	}

	public boolean isElementPresent(PageElement element, int timeoutInSeconds) {
		LOGGER.debug("Checking for the presence of the element {}", element);
		boolean isElementPresent = false;
		try {
			this.waitForElementToBePresentAndVisible(element, timeoutInSeconds);
			isElementPresent = true;
		} catch (Exception err) {
			// dont do anything
		}
		return isElementPresent;
	}

	public boolean isElementPresent(PageElement element) {
		return this.isElementPresent(element, ELEMENT_TIMEOUT_IN_SECONDS);
	}

	public void clickOnElementWithText(PageElement element, String elementText) {
		LOGGER.debug("Clicking on the element {} with text {}", element, elementText);
		final By locator = this.getByLocator(element);
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
				.withTimeout(Duration.ofSeconds(ELEMENT_TIMEOUT_IN_SECONDS)).ignoring(RuntimeException.class)
				.withMessage(String.format(ELEMENT_PRESENCE_TIMEOUT_MESSAGE, element))
				.pollingEvery(Duration.ofMillis(POLL_INTERVAL_IN_MILLIS));
		final List<WebElement> webElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		for (WebElement webElement : webElements) {
			final String webElementText = webElement.getText().trim();
			if (webElementText.equalsIgnoreCase(elementText)) {
				LOGGER.debug("Located a match for the text {}", elementText);
				webElement.click();
				return;
			}
		}
		throw new RuntimeException(String.format(NO_MATCHING_ELEMENT, element, elementText));

	}

	public void quit() {
		if (this.driver != null) {
			LOGGER.debug("Quitting the driver instance...");
			this.driver.quit();
		}
	}

	public static Bot newInstance() {
		return new WebDriverBot();
	}

}
