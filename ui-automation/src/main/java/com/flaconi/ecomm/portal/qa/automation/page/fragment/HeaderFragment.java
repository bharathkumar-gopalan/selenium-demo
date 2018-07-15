package com.flaconi.ecomm.portal.qa.automation.page.fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;

/**
 * A class representing the common header fragments, Which is displayed across
 * all the pages
 * 
 * @author bharath
 *
 */
public class HeaderFragment {
	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFragment.class);
	private final Bot bot;
	private static final String INVALID_CART_QTY_MESSAGE = "Looks like the cart quantity %s is not a number, Cannot proceed!";

	public HeaderFragment(final Bot bot) {
		this.bot = bot;
	}

	/**
	 * Get the cart quantity (a number)
	 * 
	 * @return a number indicating the cart quantity
	 */
	public int getCartQuantity() {
		LOGGER.debug("Checking the cart quantity ...");
		final String cartQuantityText = this.bot.getText(HeaderFragmentPageElement.CART_QUANTITY);
		LOGGER.debug("The quantity displayed is {}", cartQuantityText);
		try {
			return Integer.parseInt(cartQuantityText);
		} catch (Exception err) {
			throw new RuntimeException(String.format(INVALID_CART_QTY_MESSAGE, cartQuantityText));
		}
	}

}
