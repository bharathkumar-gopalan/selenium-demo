package com.flaconi.ecomm.portal.qa.automation.page.home;

import com.flaconi.ecomm.portal.qa.automation.bot.PageElement;

public enum HomePageElement implements PageElement {
	SEARCH_BOX("css;#top-search #autosuggest-form-header .left"),
	PRODUCT_SEARCH_RESULTS("css;.searchResultTextProducts"),
	DISMISS_COOKIE_NOTIFICATION_BUTTON("css;.cookie-box__close span")
	;

	private String elementLocator;

	private HomePageElement(final String elementLocator) {
		this.elementLocator = elementLocator;
	}

	public String getElementLocator() {
		return this.elementLocator;
	}

}
