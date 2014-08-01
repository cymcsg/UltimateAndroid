/*
 *  Copyright (c) 2013, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 *
 */

package com.fss.commons.demo.rebound;

import android.os.Bundle;
import com.fss.commons.uiModule.swipeback.SwipeBackActivity;
import com.fss.commons.uiModule.swipeback.SwipeBackLayout;
import com.fss.commons.demo.R;

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
