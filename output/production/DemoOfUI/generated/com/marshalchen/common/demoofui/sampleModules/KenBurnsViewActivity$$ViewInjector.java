// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class KenBurnsViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.sampleModules.KenBurnsViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165448, "field 'kenBurnsView'");
    target.kenBurnsView = (com.marshalchen.common.uiModule.kenburnsview.KenBurnsView) view;
    view = finder.findRequiredView(source, 2131165447, "field 'viewSwitcher'");
    target.viewSwitcher = (android.widget.ViewSwitcher) view;
  }

  public static void reset(com.marshalchen.common.demoofui.sampleModules.KenBurnsViewActivity target) {
    target.kenBurnsView = null;
    target.viewSwitcher = null;
  }
}
