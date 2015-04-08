package com.marshalchen.common.uimodule.listbuddies.helpers;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ListBuddiesAutoScrollHelper extends ListViewAutoScrollHelper {
    private static final String TAG = ListBuddiesAutoScrollHelper.class.getSimpleName();

    public ListBuddiesAutoScrollHelper(ListView target) {
        super(target);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return false;
        }

        return super.onTouch(v,event);
    }
}
