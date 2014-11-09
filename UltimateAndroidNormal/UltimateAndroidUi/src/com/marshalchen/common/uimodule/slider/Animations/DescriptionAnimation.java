package com.marshalchen.common.uimodule.slider.Animations;

import android.view.View;

import com.marshalchen.common.uimodule.R;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ObjectAnimator;
import com.marshalchen.common.uimodule.nineoldandroids.animation.ValueAnimator;
import com.marshalchen.common.uimodule.nineoldandroids.view.ViewHelper;

/**
 * A demo class to show how to use {@link com.marshalchen.common.uimodule.slider.Animations.BaseAnimationInterface}
 * to make  your custom animation in {@link com.marshalchen.common.uimodule.slider.Tricks.ViewPagerEx.PageTransformer} action.
 */
public class DescriptionAnimation implements BaseAnimationInterface {

    @Override
    public void onPrepareCurrentItemLeaveScreen(View current) {
        View descriptionLayout = current.findViewById(R.id.description_layout);
        if(descriptionLayout!=null){
            current.findViewById(R.id.description_layout).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * When next item is coming to show, let's hide the description layout.
     * @param next
     */
    @Override
    public void onPrepareNextItemShowInScreen(View next) {
        View descriptionLayout = next.findViewById(R.id.description_layout);
        if(descriptionLayout!=null){
            next.findViewById(R.id.description_layout).setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onCurrentItemDisappear(View view) {

    }

    /**
     * When next item show in ViewPagerEx, let's make an animation to show the
     * description layout.
     * @param view
     */
    @Override
    public void onNextItemAppear(View view) {

        View descriptionLayout = view.findViewById(R.id.description_layout);
        if(descriptionLayout!=null){
            float layoutY = ViewHelper.getY(descriptionLayout);
            view.findViewById(R.id.description_layout).setVisibility(View.VISIBLE);
            ValueAnimator animator = ObjectAnimator.ofFloat(
                    descriptionLayout,"y",layoutY + descriptionLayout.getHeight(),
                    layoutY).setDuration(500);
            animator.start();
        }

    }
}
