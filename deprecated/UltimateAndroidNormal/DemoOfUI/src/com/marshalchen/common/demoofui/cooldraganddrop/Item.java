/**
 * 
 */
package com.marshalchen.common.demoofui.cooldraganddrop;

public class Item {

	int mIcon;
	int mSpans;
	String mTitle;
	String mDescription;

	public Item(int icon, int spans, String title, String description) {

		mIcon = icon;
		mSpans = spans;
		mTitle = title;
		mDescription = description;

	}

	public int getSpans() {
		return mSpans;
	}

	public String getDescription() {
		return mDescription;
	}

	public int getIcon() {
		return mIcon;
	}

	public String getTitle() {
		return mTitle;
	}
}
