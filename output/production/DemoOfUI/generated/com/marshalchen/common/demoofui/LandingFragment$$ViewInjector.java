// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LandingFragment$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.LandingFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165453, "field 'landingEnhanceListView'");
    target.landingEnhanceListView = (com.marshalchen.common.uiModule.enhanceListView.EnhancedListView) view;
    view = finder.findRequiredView(source, 2131165450, "field 'landingMallViewpager'");
    target.landingMallViewpager = (android.support.v4.view.ViewPager) view;
    view = finder.findRequiredView(source, 2131165452, "field 'titanicTextView'");
    target.titanicTextView = (com.marshalchen.common.uiModule.titanic.TitanicTextView) view;
  }

  public static void reset(com.marshalchen.common.demoofui.LandingFragment target) {
    target.landingEnhanceListView = null;
    target.landingMallViewpager = null;
    target.titanicTextView = null;
  }
}
