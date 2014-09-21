/*
 *  Copyright (c) 2013, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 *
 */

package com.marshalchen.common.demoofui.rebound;

import android.os.Bundle;
import com.marshalchen.common.uimodule.swipeback.SwipeBackActivity;
import com.marshalchen.common.uimodule.swipeback.SwipeBackLayout;
import com.marshalchen.common.demoofui.R;

public class ReboundActivity extends SwipeBackActivity {

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rebound_activity);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEnabled(true);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
    }

}
