package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.ui.ExpandedCircleProgressView;

public class ExpandCircleProgressExampleActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_circle_progress_activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new ExtendedCircleFragment()).commit();
		}
	}

	public class ExtendedCircleFragment extends Fragment {

		private ExpandedCircleProgressView mCircleProgressView;
		protected int mProgress = 0;
		private Handler mHandler = new Handler() {
			private boolean mIsDecresed = false;

			public void handleMessage(android.os.Message msg) {

				if (mProgress == 60 && !mIsDecresed) {
					mProgress = mProgress - 20;
					mIsDecresed = true;
				} else {
					mProgress = mProgress + 20;
				}
				mCircleProgressView.setProgress(mProgress);
				if(mProgress<100) sendEmptyMessageDelayed(0, 1000);
			};
		};

		public ExtendedCircleFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.expand_circle_progress_fragment_main, container,
					false);
			mCircleProgressView = (ExpandedCircleProgressView) rootView.findViewById(R.id.expanded_circle_progress);
			mHandler.sendEmptyMessageDelayed(0, 1000);
			return rootView;
		}

		@Override
		public void onDestroy() {
			super.onDestroy();			
			// you must call this method to avoid expanding forever
			if(mCircleProgressView!=null) mCircleProgressView.cancelUpdateTask();
		}

	}
}
