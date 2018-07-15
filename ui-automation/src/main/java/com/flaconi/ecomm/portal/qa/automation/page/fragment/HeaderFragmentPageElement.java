package com.flaconi.ecomm.portal.qa.automation.page.fragment;

import com.flaconi.ecomm.portal.qa.automation.bot.PageElement;

public enum HeaderFragmentPageElement implements PageElement{
	

	CART_DETAILS("css;.cart-details"),
	CART_EMPTY("css;.empty"),
	CART_QUANTITY("css;.ico-basket .quantity"),
	;

	private String elementLocator;

	private HeaderFragmentPageElement(final String elementLocator) {
		this.elementLocator = elementLocator;
	}

	public String getElementLocator() {
		return this.elementLocator;
	}

}
