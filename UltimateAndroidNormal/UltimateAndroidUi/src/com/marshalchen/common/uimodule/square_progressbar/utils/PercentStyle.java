package com.marshalchen.common.uimodule.square_progressbar.utils;

import android.graphics.Color;
import android.graphics.Paint.Align;

public class PercentStyle {
	private Align align;
	private float textSize;
	private boolean percentSign;
	private String customText = "%";
	private int textColor = Color.BLACK;

	public PercentStyle() {
		// do nothing
	}

	public PercentStyle(Align align, float textSize, boolean percentSign) {
		super();
		this.align = align;
		this.textSize = textSize;
		this.percentSign = percentSign;
	}

	public Align getAlign() {
		return align;
	}

	public void setAlign(Align align) {
		this.align = align;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public boolean isPercentSign() {
		return percentSign;
	}

	public void setPercentSign(boolean percentSign) {
		this.percentSign = percentSign;
	}

	public String getCustomText() {
		return customText;
	}

	/**
	 * With this you can set a custom text which should get displayed right
	 * behind the number of the progress. Per default it displays a <i>%</i>.
	 * 
	 * @param customText
	 *            The custom text you want to display.
	 * @since 1.4.0
	 */
	public void setCustomText(String customText) {
		this.customText = customText;
	}

	public int getTextColor() {
		return textColor;
	}

	/**
	 * Set the color of the text that display the current progress. This will
	 * also change the color of the text that normally represents a <i>%</i>.
	 * 
	 * @param textColor
	 *            the color to set the text to.
	 * @since 1.4.0
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

}