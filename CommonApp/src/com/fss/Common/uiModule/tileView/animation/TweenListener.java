package com.fss.Common.uiModule.tileView.animation;

public interface TweenListener {
	public void onTweenStart();
	public void onTweenProgress(double progress, double eased);
	public void onTweenComplete();
}
