// Generated code from Butter Knife. Do not modify!
package com.fss.common.demo.sampleModules;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PhotoViewActivity$$ViewInjector {
  public static void inject(Finder finder, final com.fss.common.demo.sampleModules.PhotoViewActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165546, "field 'photoViewImageView'");
    target.photoViewImageView = (android.widget.ImageView) view;
  }

  public static void reset(com.fss.common.demo.sampleModules.PhotoViewActivity target) {
    target.photoViewImageView = null;
  }
}
