package com.marshalchen.common.demoofui.BlurNavigationDrawer;

import android.graphics.drawable.Drawable;

/**
 * Created by charbgr on 8/30/14.
 */
public class Movie {

    private Drawable drawable;
    private String title;

    public Movie(Drawable drawable, String title) {
        this.drawable = drawable;
        this.title = title;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String getTitle() {
        return title;
    }
}
