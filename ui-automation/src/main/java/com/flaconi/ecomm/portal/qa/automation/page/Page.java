package com.flaconi.ecomm.portal.qa.automation.page;

import com.flaconi.ecomm.portal.qa.automation.bot.Bot;
import com.flaconi.ecomm.portal.qa.automation.page.fragment.HeaderFragment;

/**
 * An abstraction representing a page . The page classes must extend this class
 * and implement the load and isLoaded methods. When an application has multiple
 * pages , It is possible to include the parent page into the child pages and
 * 'pull' the parent page and navigate to the child page.
 * 
 * 
 * @author bharath
 *
 * @param <T>
 *            The class implementing this Page class
 */
public abstract class Page<T> {
	private static final String PAGE_NOT_LOADED_ERROR_MESSAGE = "The page %s is not loaded correctly";
	// The header fragment that is common across all the pages
	private final HeaderFragment headerFragment;
	
	
	public Page(final Bot bot){
		this.headerFragment = new HeaderFragment(bot);
	}

	/**
	 * Check if the page in question is loaded
	 * 
	 * @return a boolean value indicating if the page is loaded or not
	 */
	public abstract boolean isLoaded();

	/**
	 * Load this particular page
	 * 
	 */
	public abstract void load();
	
	
	/**
	 * Get this page 
	 * 
	 * @return A handle to the page instance 
	 */
	@SuppressWarnings("unchecked")
	public T get() {
		this.load();
		if (this.isLoaded() == false) {
			throw new RuntimeException(String.format(PAGE_NOT_LOADED_ERROR_MESSAGE, this.getClass().getSimpleName()));
		}
		return (T) this;
	}
	
	
	public HeaderFragment getHeaderFragment(){
		return this.headerFragment;
	}

}
