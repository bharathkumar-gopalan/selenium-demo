package com.flaconi.ecomm.portal.qa.automation.test.cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;
import com.flaconi.ecomm.portal.qa.automation.page.cart.CartPage;
import com.flaconi.ecomm.portal.qa.automation.page.home.HomePage;
import com.flaconi.ecomm.portal.qa.automation.page.product.ProductPage;
import com.flaconi.ecomm.portal.qa.automation.test.base.BaseTest;

public class CartAdditionTest extends BaseTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(CartAdditionTest.class);
	private Bot bot;
	private HomePage homePage;
	private ProductPage productPage;
	private CartPage cartPage;

	// Failure Assertion messages
	private static final String CART_ADDITION_FAILURE_MESSAGE = "When the Product %s (SKU ID %s) item is selected to be added to the cart , it is successfully added ";
	private static final String CART_QUANTITY_INCREMENT_FAILURE_MESSAGE = "When the Product %s (SKU ID %s) added to the cart , The cart quantity is incremented ";

	@BeforeClass(alwaysRun = true)
	public void initialize() {
		LOGGER.debug("Starting the test case {}", this.getClass().getSimpleName());
		final String homePageUrl = this.getHomePageUrl();
		this.bot = this.getBotInstance();
		this.homePage = new HomePage(this.bot, homePageUrl);
	}

	/**
	 * Verify if the user is able to search for a product and is able to add the
	 * item to the cart. Please note that this test case has a limitation , When
	 * the product is not listed in the test results this test will fail(The
	 * failure is handled correctly in the framework).  
	 * 
	 * @param searchString
	 *            The product search string
	 * @param productFullName
	 *            The product full name to select
	 * @param skuID
	 *            The SKU id of the product
	 */
	@Test(dataProvider = "productDataProvider", description = "Check to see if the user is able to search and add a given product with a given SKU id to the cart , Verifty if the cart quantity is incremented")
	public void testItemAdditionToCart(final String searchString, final String productFullName, final String skuID) {
		// Fetch the home page
		this.homePage.get();
		// Dismiss the cookie notification if displayed
		this.homePage.dismissCookieNotification();
		// Search and navigate to the product info page
		productPage = this.homePage.searchAndNavigateToProductInfoPage(searchString, productFullName);
		final int initialCartQuantity = productPage.getHeaderFragment().getCartQuantity();
		LOGGER.debug("The initial cart quantity is {}", initialCartQuantity);
		cartPage = productPage.addSkuItemToCart(skuID);
		final int finalCartQuantity = cartPage.getHeaderFragment().getCartQuantity();
		LOGGER.debug("The final cart quantity is {}", finalCartQuantity);
		// Check if the product is added successfully
		Assert.assertTrue(cartPage.isSkuItemInCart(skuID),
				String.format(CART_ADDITION_FAILURE_MESSAGE, productFullName, skuID));
		// Check if the cart count is incremented by (usually by 1)
		Assert.assertTrue(finalCartQuantity > initialCartQuantity,
				String.format(CART_QUANTITY_INCREMENT_FAILURE_MESSAGE, productFullName, skuID));

	}

	@DataProvider(name = "productDataProvider")
	public Object[][] productDataProvider() {
		return new String[][] {

				{ "Calvin klein", "Calvin klein ck one Eau de Toilette", "20101463" },
				{ "Dolce & Gabbana", "Dolce & Gabbana The one Eau de Parfum", "20101852" }

		};

	}

}
