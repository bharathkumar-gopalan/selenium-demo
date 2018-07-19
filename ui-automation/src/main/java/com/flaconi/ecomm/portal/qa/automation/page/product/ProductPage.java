package com.flaconi.ecomm.portal.qa.automation.page.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;
import com.flaconi.ecomm.portal.qa.automation.bot.PageElement;
import com.flaconi.ecomm.portal.qa.automation.page.Page;
import com.flaconi.ecomm.portal.qa.automation.page.cart.CartPage;

public class ProductPage extends Page<ProductPage> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPage.class);
	private static final int NOTIFICATION_ELEMENT_TIMEOUT_IN_SECONDS = 5;
	// Error messages
	private static final String NO_SKU_ITEM_FOUND_MESSAGE = "Unable to find the sku item %s in the product page";
	private final Bot bot;

	public ProductPage(final Bot bot) {
		super(bot);
		this.bot = bot;
	}

	@Override
	public boolean isLoaded() {
		return this.bot.isElementPresent(ProductPageElement.PRODUCT_NAME_HEADER);
	}

	@Override
	public void load() {
		// We dont need this for now , As this page is navigated from the search
		// page
	}

	/**
	 * Add a SKU item to the cart .Every item displayed in the page can be
	 * uniquely identified with a SKU ID. We use that to dynamically construct
	 * an element locator and add it to the cart. Once added the system
	 * navigates to the cart page
	 * 
	 * @param skuId
	 *            The SKU id of the item that needs to be added to the cart           
	 * @return
	 * 		An instance of the cart page 
	 */
	public CartPage addSkuItemToCart(final String skuId) {
		LOGGER.debug("Attempting to add the sku item {} to the cart");
		final String dynamicSkuElement = String.format(ProductPageElement.DYNAMIC_SKU_LOCATOR.getElementLocator(), skuId);
		final boolean isSkuItemLocated = this.bot.isElementPresent(new PageElement() {
			public String getElementLocator() {
				return dynamicSkuElement;
			}
		});
		// If the sku item is not found in the page then throw an error message
		if (isSkuItemLocated == false) {
			throw new RuntimeException(String.format(NO_SKU_ITEM_FOUND_MESSAGE, skuId));
		}
		this.bot.click(new PageElement() {
			public String getElementLocator() {
				return dynamicSkuElement + ProductPageElement.SKU_ITEM_BUTTON_LOCATOR.getElementLocator();
			}

		});
		if(this.bot.isElementPresent(ProductPageElement.FREQUENTLY_BOUGHT_ITEM_OVERLAY, NOTIFICATION_ELEMENT_TIMEOUT_IN_SECONDS)){
			LOGGER.debug("The freqently bought item overlay is displayed , dismissing the same for now ");
			this.bot.click(ProductPageElement.PROCEED_TO_CART_BUTON);
		}
		final CartPage cartPage = new CartPage(this.bot);
		cartPage.isLoaded();
		return cartPage;
	}
	
	
	


}
