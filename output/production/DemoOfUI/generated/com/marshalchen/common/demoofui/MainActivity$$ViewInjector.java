// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165391, "field 'mDrawerLayout'");
    target.mDrawerLayout = (android.support.v4.widget.DrawerLayout) view;
    view = finder.findRequiredView(source, 2131165493, "field 'mDrawerList'");
    target.mDrawerList = (android.widget.ListView) view;
    view = finder.findRequiredView(source, 2131165492, "field 'favShimmerTextView'");
    target.favShimmerTextView = (com.marshalchen.common.uiModule.shimmer.ShimmerTextView) view;
    view = finder.findRequiredView(source, 2131165490, "field 'main_content_frame'");
    target.main_content_frame = view;
    view = finder.findRequiredView(source, 2131165491, "field 'favShimmerReaLayout'");
    target.favShimmerReaLayout = view;
  }

  public static void reset(com.marshalchen.common.demoofui.MainActivity target) {
    target.mDrawerLayout = null;
    target.mDrawerList = null;
    target.favShimmerTextView = null;
    target.main_content_frame = null;
    target.favShimmerReaLayout = null;
  }
}
