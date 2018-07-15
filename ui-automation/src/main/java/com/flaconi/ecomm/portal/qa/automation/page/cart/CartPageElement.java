package com.flaconi.ecomm.portal.qa.automation.page.cart;

import com.flaconi.ecomm.portal.qa.automation.bot.PageElement;

public enum CartPageElement implements PageElement{
	
	CART_DETAILS("css;.cart-details"),
	ITEM_REMOVED_CONFIRMATION_ALERT("css;.alert-box.success"),
	CART_EMPTY("css;.empty"),
	DYNAMIC_SKU_LOCATOR("xpath;//div[@data-sku='%s']"),
	DYNAMIC_SKU_CLEAR_ELEMENT_LOCATOR("id;delete-from-cart-%s")
	
	;

	private String elementLocator;

	private CartPageElement(final String elementLocator) {
		this.elementLocator = elementLocator;
	}

	public String getElementLocator() {
		return this.elementLocator;
	}

}
