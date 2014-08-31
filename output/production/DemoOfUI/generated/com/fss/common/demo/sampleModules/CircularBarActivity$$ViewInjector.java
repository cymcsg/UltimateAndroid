// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CircularBarActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.CircularBarActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165338, "field 'circularProgressBar'");
    target.circularProgressBar = (com.marshalchen.common.ui.HoloCircularProgressBar) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.CircularBarActivity target) {
    target.circularProgressBar = null;
  }
}
