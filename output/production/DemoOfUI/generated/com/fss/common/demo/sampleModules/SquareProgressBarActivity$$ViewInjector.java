// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SquareProgressBarActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.SquareProgressBarActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165611, "field 'squareProgressBar'");
    target.squareProgressBar = (com.marshalchen.common.uiModule.square_progressbar.SquareProgressBar) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.SquareProgressBarActivity target) {
    target.squareProgressBar = null;
  }
}
