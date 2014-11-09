package com.marshalchen.common.uimodule.listbuddies.views.containers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.marshalchen.common.uimodule.R;


/**
 * RelativeLayout with a touch feedback color as overlay.
 */
public class LinearLayoutFeedback extends LinearLayout {

    private StateListDrawable touchFeedbackDrawable;

    public LinearLayoutFeedback(Context context) {
        super(context);
    }

    public LinearLayoutFeedback(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SelectorOptions, 0, 0);
        setSelector(a);

    }


    private void setSelector(TypedArray a) {
        touchFeedbackDrawable = new StateListDrawable();
        touchFeedbackDrawable.addState(
                new int[]{android.R.attr.state_pressed},
                getColor(a)
        );
    }

    private Drawable getColor(TypedArray a) {
        return new ColorDrawable(
                a.getColor(R.styleable.SelectorOptions_selectorColor,
                        android.R.color.darker_gray)
        );
    }

    public LinearLayoutFeedback(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


    }

    @Override
    public void setPressed(boolean pressed) {
        // If the parent is pressed, do not set to pressed.
        if (pressed && ((View) getParent()).isPressed()) {
            return;
        }
        super.setPressed(pressed);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (touchFeedbackDrawable != null) {
            touchFeedbackDrawable.setBounds(0, 0, getWidth(), getHeight());
            touchFeedbackDrawable.draw(canvas);
        }
    }

    @Override
    protected void drawableStateChanged() {
        if (touchFeedbackDrawable != null) {
            touchFeedbackDrawable.setState(getDrawableState());
            invalidate();
        }
        super.drawableStateChanged();
    }
}
