package com.marshalchen.common.demoofui.sampleModules;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.jazzyviewpager.JazzyViewPager;
import com.marshalchen.common.uimodule.jazzyviewpager.OutlineContainer;

public class JazzyViewActivity extends Activity {

	private JazzyViewPager mJazzy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jazzy_view_activity_main);
		setupJazziness(JazzyViewPager.TransitionEffect.Tablet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Toggle Fade");
		String[] effects = this.getResources().getStringArray(R.array.jazzy_effects);
		for (String effect : effects)
			menu.add(effect);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("Toggle Fade")) {
			mJazzy.setFadeEnabled(!mJazzy.getFadeEnabled());
		} else {
			JazzyViewPager.TransitionEffect effect = JazzyViewPager.TransitionEffect.valueOf(item.getTitle().toString());
			setupJazziness(effect);
		}
		return true;
	}

	private void setupJazziness(JazzyViewPager.TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter());
		mJazzy.setPageMargin(30);
	}

	private class MainAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			TextView text = new TextView(JazzyViewActivity.this);
			text.setGravity(Gravity.CENTER);
			text.setTextSize(30);
			text.setTextColor(Color.WHITE);
			text.setText("Page " + position);
			text.setPadding(30, 30, 30, 30);
			int bg = Color.rgb((int) Math.floor(Math.random()*128)+64, 
					(int) Math.floor(Math.random()*128)+64,
					(int) Math.floor(Math.random()*128)+64);
			text.setBackgroundColor(bg);
			container.addView(text, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			mJazzy.setObjectForPosition(text, position);
			return text;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object obj) {
			container.removeView(mJazzy.findViewFromObject(position));
		}
		@Override
		public int getCount() {
			return 10;
		}
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}		
	}
}
