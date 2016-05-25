package com.marshalchen.common.uimodule.matchview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.marshalchen.common.uimodule.matchview.util.MatchPath;


/**
 * Description:MatchButton View
 * User: Lj
 * Date: 2014-12-03
 * Time: 13:53
 * FIXME
 */
public class MatchButton extends MatchTextView {


    public MatchButton(Context context) {
        super(context);
        init();
    }

    public MatchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    void init() {
        PraseText();
        super.init();
    }

    void PraseText() {
        if (!TextUtils.isEmpty(mContent)) {
            MatchPath.isButtonModle = true;
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(MatchPath.V_LEFT);
            strBuffer.append(mContent);
            strBuffer.append(MatchPath.V_RIGHT);
            this.mContent = strBuffer.toString();
        }
    }
}
