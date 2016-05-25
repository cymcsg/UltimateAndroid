/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.marshalchen.common.uimodule.cardsSwiped.model;

public class Likes {
	 public enum Like {
	        None(0), Liked(1), Disliked(2);

	        public final int value;

	        private Like(int value) {
	            this.value = value;
	        }

	        public static Like fromValue(int value) {
	            for (Like style : Like.values()) {
	                if (style.value == value) {
	                    return style;
	                }
	            }
	            return null;
	        }
	    }
}
