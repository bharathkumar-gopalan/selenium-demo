package com.flaconi.ecomm.portal.qa.automation.page.product;

import com.flaconi.ecomm.portal.qa.automation.bot.PageElement;

public enum ProductPageElement implements PageElement{
	PRODUCT_NAME_HEADER("css;.product-name"),
	USER_CART("css;.ico-basket"),
	DYNAMIC_SKU_LOCATOR("xpath;//li[@data-sku='%s']"),
	SKU_ITEM_BUTTON_LOCATOR("//button"),
	FREQUENTLY_BOUGHT_ITEM_OVERLAY("class;overlay"),
	CLOSE_BUTTON("class;ico-close"),
	PROCEED_TO_CART_BUTON("xpath;//a[@class='button-secondary pull-right']")
	;
	
	
	
	private String elementLocator;

	private ProductPageElement(final String elementLocator) {
		this.elementLocator = elementLocator;
	}

	public String getElementLocator() {
		return this.elementLocator;
	}

}
