/*
 * Copyright (c) 2013 Android Alliance, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marshalchen.common.uimodule.edgeeffectoverride;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.marshalchen.ultimateandroiduicomponent.R;

public class EdgeEffectExpandableListView extends android.widget.ExpandableListView {

  public EdgeEffectExpandableListView(Context context) {
    this(context, null);
  }

	public EdgeEffectExpandableListView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.expandableListViewStyle);
	}

	public EdgeEffectExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(new ContextWrapperEdgeEffect(context), attrs, defStyle);
    init(context, attrs, defStyle);
	}

  private void init(Context context, AttributeSet attrs, int defStyle){
    int color = context.getResources().getColor(R.color.default_edgeeffect_color);

    if (attrs != null) {
      TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EdgeEffectView, defStyle, 0);
      color = a.getColor(R.styleable.EdgeEffectView_edgeeffect_color, color);
      a.recycle();
    }
    setEdgeEffectColor(color);
  }

  public void setEdgeEffectColor(int edgeEffectColor){
    ((ContextWrapperEdgeEffect)  getContext()).setEdgeEffectColor(edgeEffectColor);
  }
}
