// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PanningViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.sampleModules.PanningViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165526, "field 'panningView'");
    target.panningView = (com.marshalchen.common.uiModule.panningview.PanningView) view;
  }

  public static void reset(com.marshalchen.common.demoofui.sampleModules.PanningViewActivity target) {
    target.panningView = null;
  }
}
