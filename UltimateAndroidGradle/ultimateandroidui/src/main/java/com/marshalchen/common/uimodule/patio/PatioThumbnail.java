package com.marshalchen.common.uimodule.patio;

import android.widget.ImageView;

public class PatioThumbnail {
    private String mThumbnailPath;
    private ImageView mThumbnailView;

    public PatioThumbnail(String thumbnailPath, ImageView thumbnailView) {
        mThumbnailPath = thumbnailPath;
        mThumbnailView = thumbnailView;
    }

    public String getThumbnailPath() {
        return mThumbnailPath;
    }

    public ImageView getThumbnailView() {
        return mThumbnailView;
    }

    public void setSelected(boolean selected) {
        getThumbnailView().setSelected(selected);
        if(selected) {
            getThumbnailView().setAlpha(0.5f);
        } else {
            getThumbnailView().setAlpha(1.0f);
        }
    }

    public boolean isSelected() {
        return getThumbnailView().isSelected();
    }
}
