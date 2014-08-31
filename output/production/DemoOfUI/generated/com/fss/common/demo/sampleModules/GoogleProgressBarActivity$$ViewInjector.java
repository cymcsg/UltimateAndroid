// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GoogleProgressBarActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.GoogleProgressBarActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165421, "field 'mProgressBar'");
    target.mProgressBar = (com.marshalchen.common.uiModule.googleprogressbar.GoogleProgressBar) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.GoogleProgressBarActivity target) {
    target.mProgressBar = null;
  }
}
