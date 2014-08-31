// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CircularBarActivity$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.sampleModules.CircularBarActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165338, "field 'circularProgressBar'");
    target.circularProgressBar = (com.marshalchen.common.ui.HoloCircularProgressBar) view;
  }

  public static void reset(com.marshalchen.common.demoofui.sampleModules.CircularBarActivity target) {
    target.circularProgressBar = null;
  }
}
