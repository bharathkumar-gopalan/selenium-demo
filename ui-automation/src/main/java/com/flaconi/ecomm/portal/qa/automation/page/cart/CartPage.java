package com.flaconi.ecomm.portal.qa.automation.page.cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;
import com.flaconi.ecomm.portal.qa.automation.bot.PageElement;
import com.flaconi.ecomm.portal.qa.automation.page.Page;

public class CartPage extends Page<CartPage> {
	private final Bot bot;
	private static final Logger LOGGER = LoggerFactory.getLogger(CartPage.class);

	// Error messages
	

	public CartPage(final Bot bot) {
		super(bot);
		this.bot = bot;
	}

	@Override
	public boolean isLoaded() {
		return this.bot.isElementPresent(CartPageElement.CART_DETAILS);
	}

	@Override
	public void load() {
		// We dont need this for now , As this page is navigated from the
		// product page
	}

	/**
	 * Check to see if an SKU item is in cart
	 * 
	 * @param skuId
	 *            The Sku id of the product to be removed
	 * 
	 * @return a boolean value indicating if the item is in cart or not
	 * 
	 */
	public boolean isSkuItemInCart(final String skuId) {
		LOGGER.debug("Checking to see if the sku item {} is in cart", skuId);
		final String elementLocator = String.format(CartPageElement.DYNAMIC_SKU_LOCATOR.getElementLocator(), skuId);
		return this.bot.isElementPresent(new PageElement() {
			public String getElementLocator() {
				return elementLocator;
			}
		});
	}

	/**
	 * Clear a particular item from the cart
	 * 
	 * @param skuId
	 *            The Sku id of the product to be removed
	 * @return boolean value indicating that the item is removed successfully or
	 *         not
	 */
	public boolean removeSkuItem(final String skuId) {
		LOGGER.debug("Attmpting to remove the sku item {} ", skuId);
		final String elementLocator = String
				.format(CartPageElement.DYNAMIC_SKU_CLEAR_ELEMENT_LOCATOR.getElementLocator(), skuId);
		this.bot.click(new PageElement() {
			public String getElementLocator() {
				return elementLocator;
			}
		});
		LOGGER.debug("Clicked to remove the item ");
		return bot.isElementPresent(CartPageElement.ITEM_REMOVED_CONFIRMATION_ALERT);
	}

	/**
	 * Check if the cart is empty , Verifies to see if the css element empty is
	 * displayed
	 * 
	 * @return boolean value indicating if the cart is empty or not
	 * 
	 */
	public boolean isCartEmpty() {
		LOGGER.debug("Checking to see if the cart is empty");
		return bot.isElementPresent(CartPageElement.CART_EMPTY);
	}

	

}
