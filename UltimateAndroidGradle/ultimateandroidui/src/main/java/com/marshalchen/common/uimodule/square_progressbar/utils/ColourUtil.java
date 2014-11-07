package com.marshalchen.common.uimodule.square_progressbar.utils;

import android.R.color;

import java.util.ArrayList;

public class ColourUtil {
	static ArrayList<Integer> colourArray = new ArrayList<Integer>();

	public static ArrayList<Integer> getColourArray() {
		colourArray.add(color.holo_blue_bright);
		colourArray.add(color.holo_blue_dark);
		colourArray.add(color.holo_blue_light);
		colourArray.add(color.holo_green_dark);
		colourArray.add(color.holo_green_light);
		colourArray.add(color.holo_orange_dark);
		colourArray.add(color.holo_orange_light);
		colourArray.add(color.holo_purple);
		colourArray.add(color.holo_red_dark);
		colourArray.add(color.holo_red_light);
		return colourArray;
	}
}
