// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SmoothProgressBarActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.SmoothProgressBarActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165607, "field 'smoothProgressBar'");
    target.smoothProgressBar = (com.marshalchen.common.uiModule.smoothprogressbar.SmoothProgressBar) view;
    view = finder.findRequiredView(source, 2131165608, "field 'smoothProgressBar1'");
    target.smoothProgressBar1 = (com.marshalchen.common.uiModule.smoothprogressbar.SmoothProgressBar) view;
    view = finder.findRequiredView(source, 2131165609, "field 'smoothProgressBar2'");
    target.smoothProgressBar2 = (com.marshalchen.common.uiModule.smoothprogressbar.SmoothProgressBar) view;
    view = finder.findRequiredView(source, 2131165610, "field 'smoothProgressBar3'");
    target.smoothProgressBar3 = (com.marshalchen.common.uiModule.smoothprogressbar.SmoothProgressBar) view;
  }

  public static void reset(com.fss.common.demo.SmoothProgressBarActivity target) {
    target.smoothProgressBar = null;
    target.smoothProgressBar1 = null;
    target.smoothProgressBar2 = null;
    target.smoothProgressBar3 = null;
  }
}
