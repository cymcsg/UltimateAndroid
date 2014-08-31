// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui.rebound;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ReboundActivitySimple$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.rebound.ReboundActivitySimple target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165572, "field 'reboundImageView'");
    target.reboundImageView = (android.widget.ImageView) view;
  }

  public static void reset(com.marshalchen.common.demoofui.rebound.ReboundActivitySimple target) {
    target.reboundImageView = null;
  }
}
