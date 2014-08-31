// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.rebound;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ReboundActivitySimple$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.rebound.ReboundActivitySimple target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165572, "field 'reboundImageView'");
    target.reboundImageView = (android.widget.ImageView) view;
  }

  public static void reset(com.fss.common.demo.rebound.ReboundActivitySimple target) {
    target.reboundImageView = null;
  }
}
