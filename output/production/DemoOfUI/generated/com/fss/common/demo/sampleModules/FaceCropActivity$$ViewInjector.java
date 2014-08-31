// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FaceCropActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.FaceCropActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165389, "field 'faceCropImageView'");
    target.faceCropImageView = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131165390, "field 'faceCropImageViewCroped'");
    target.faceCropImageViewCroped = (android.widget.ImageView) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.FaceCropActivity target) {
    target.faceCropImageView = null;
    target.faceCropImageViewCroped = null;
  }
}
