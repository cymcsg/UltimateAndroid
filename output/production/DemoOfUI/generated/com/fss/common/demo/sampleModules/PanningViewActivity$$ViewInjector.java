// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PanningViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.PanningViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165526, "field 'panningView'");
    target.panningView = (com.marshalchen.common.uiModule.panningview.PanningView) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.PanningViewActivity target) {
    target.panningView = null;
  }
}
