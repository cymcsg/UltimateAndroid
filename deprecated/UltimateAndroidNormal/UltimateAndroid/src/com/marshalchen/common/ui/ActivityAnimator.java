package com.marshalchen.common.ui;


import android.app.Activity;
import com.marshalchen.common.R;


public class ActivityAnimator
{
	public void flipHorizontalAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_flip_horizontal_in, R.anim.ac_transition_flip_horizontal_out);
	}
	
	public void flipVerticalAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_flip_vertical_in, R.anim.ac_transition_flip_vertical_out);
	}
	
	public void fadeAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_fade_in, R.anim.ac_transition_fade_out);
	}
	
	public void disappearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_disappear_top_left_in, R.anim.ac_transition_disappear_top_left_out);
	}
	
	public void appearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_appear_top_left_in, R.anim.ac_transition_appear_top_left_out);
	}
	
	public void disappearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_disappear_bottom_right_in, R.anim.ac_transition_disappear_bottom_right_out);
	}
	
	public void appearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_appear_bottom_right_in, R.anim.ac_transition_appear_bottom_right_out);
	}
	
	public void unzoomAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.ac_transition_unzoom_in, R.anim.ac_transition_unzoom_out);
	}
}
