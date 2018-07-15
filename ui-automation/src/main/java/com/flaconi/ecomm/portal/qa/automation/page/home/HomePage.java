package com.flaconi.ecomm.portal.qa.automation.page.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;
import com.flaconi.ecomm.portal.qa.automation.page.Page;
import com.flaconi.ecomm.portal.qa.automation.page.fragment.HeaderFragment;
import com.flaconi.ecomm.portal.qa.automation.page.product.ProductPage;

/**
 * A class representing the flaconi ecomm home page
 * 
 * @author bharath
 *
 */
public class HomePage extends Page<HomePage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);
	private final Bot bot;
	private final String baseUrl;
	private final HeaderFragment headerFragment;

	// Error messages
	private static final String NO_AUTO_SUGGEST_RESULTS_MESSAGE = "Looks like there are no auto suggest results for the product %s when searched with %s  , Automation cannot continue";

	public HomePage(final Bot bot, final String baseUrl) {
		super(bot);
		this.bot = bot;
		this.baseUrl = baseUrl;
		this.headerFragment = new HeaderFragment(this.bot);
	}

	@Override
	public boolean isLoaded() {
		return bot.isElementPresent(HomePageElement.SEARCH_BOX);
	}

	@Override
	public void load() {
		LOGGER.debug("Loading the home page @ url {}", baseUrl);
		this.bot.goToUrl(baseUrl);
	}

	/**
	 * Search for an item and navigate to its product page . Please do note that
	 * this functionality is limited , For example if the search returns a large
	 * number of items we have to do navigate across multiple pages and select
	 * the item - this function does not handle that, It demonstrates a simple
	 * use case
	 * 
	 * @param searchString
	 *            The search string to enter in the text box
	 * @param productName
	 *            The full product name to select
	 * @return The product page of the appropriate item
	 * 
	 */
	public ProductPage searchAndNavigateToProductInfoPage(final String searchString, final String productName) {
		LOGGER.debug("Searching for the product {}", searchString);
		// Note - Only when we click and send keys the search box is responsive
		// giving auto suggest values
		this.bot.click(HomePageElement.SEARCH_BOX);
		// Search for the product
		this.bot.type(HomePageElement.SEARCH_BOX, searchString);
		// now select the particular product
		try {
			this.bot.clickOnElementWithText(HomePageElement.PRODUCT_SEARCH_RESULTS, productName);
		} catch (Exception err) {
			// Most of the time the auto suggest results is not loaded. In that
			// case we throw an error message and stop
			throw new RuntimeException(String.format(NO_AUTO_SUGGEST_RESULTS_MESSAGE, productName, searchString));
		}
		final ProductPage productPage = new ProductPage(this.bot);
		productPage.isLoaded();
		return productPage;
	}

	/**
	 * Get the page header fragment , This will expose the methods that are
	 * available in the page fragment which can be used for validation
	 * 
	 * @return
	 */
	public HeaderFragment getHeaderFragment() {
		return this.headerFragment;
	}

	public void dismissCookieNotification() {
		// wait for 5 seconds 
		if (this.bot.isElementPresent(HomePageElement.DISMISS_COOKIE_NOTIFICATION_BUTTON, 5)) {
			LOGGER.debug("The Cookie button is displayed , Dismissing the same ");
			this.bot.click(HomePageElement.DISMISS_COOKIE_NOTIFICATION_BUTTON);
		}
	}

}
