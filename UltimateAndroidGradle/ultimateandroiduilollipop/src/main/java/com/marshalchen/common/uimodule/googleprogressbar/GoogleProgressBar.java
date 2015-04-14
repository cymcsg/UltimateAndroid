package com.marshalchen.common.uimodule.googleprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.marshalchen.ultimateandroiduilollipop.R;


public class GoogleProgressBar extends ProgressBar {

    private enum ProgressType{
        FOLDING_CIRCLES,
        GOOGLE_MUSIC_DICES,
        NEXUS_ROTATION_CROSS
    }

    public GoogleProgressBar(Context context) {
        this(context, null);
    }

    public GoogleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.progressBarStyle);
    }

    public GoogleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GoogleProgressBar, defStyle, 0);
        final int typeIndex = a.getInteger(R.styleable.GoogleProgressBar_type, context.getResources().getInteger(R.integer.default_type));
        final int colorsId = a.getResourceId(R.styleable.GoogleProgressBar_colors, R.array.google_colors);
        a.recycle();

        Drawable drawable = buildDrawable(context,typeIndex,colorsId);
        if(drawable!=null)
        setIndeterminateDrawable(drawable);
    }

    private Drawable buildDrawable(Context context, int typeIndex,int colorsId) {
        Drawable drawable = null;
        ProgressType type = ProgressType.values()[typeIndex];
        switch (type){
            case FOLDING_CIRCLES:
                drawable = new FoldingCirclesDrawable.Builder(context)
                        .colors(getResources().getIntArray(colorsId))
                        .build();
                break;
            case GOOGLE_MUSIC_DICES:
                drawable = new GoogleMusicDicesDrawable.Builder()
                        .build();
                break;
            case NEXUS_ROTATION_CROSS:
                drawable = new NexusRotationCrossDrawable.Builder(context)
                        .colors(getResources().getIntArray(colorsId))
                        .build();
        }

        return drawable;
    }
}
