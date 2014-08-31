// Generated code from Butter Knife. Do not modify!
package com.marshalchen.common.demoofui.jsoup;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class UtilsDemoActivity$$ViewInjector {
  public static void inject(Finder finder, final com.marshalchen.common.demoofui.jsoup.UtilsDemoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165446, "field 'mJsoupTextView'");
    target.mJsoupTextView = (android.widget.TextView) view;
  }

  public static void reset(com.marshalchen.common.demoofui.jsoup.UtilsDemoActivity target) {
    target.mJsoupTextView = null;
  }
}
