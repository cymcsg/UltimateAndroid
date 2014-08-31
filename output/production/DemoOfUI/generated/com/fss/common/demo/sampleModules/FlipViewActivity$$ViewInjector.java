// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FlipViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.FlipViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165398, "field 'flipViewListHorizontal'");
    target.flipViewListHorizontal = (com.marshalchen.common.uiModule.flipViews.flipview.FlipView) view;
    view = finder.findRequiredView(source, 2131165397, "field 'flipViewListVertical'");
    target.flipViewListVertical = (com.marshalchen.common.uiModule.flipViews.flipview.FlipView) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.FlipViewActivity target) {
    target.flipViewListHorizontal = null;
    target.flipViewListVertical = null;
  }
}
