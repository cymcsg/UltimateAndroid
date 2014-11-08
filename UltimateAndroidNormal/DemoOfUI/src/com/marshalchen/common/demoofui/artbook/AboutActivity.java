package com.marshalchen.common.demoofui.artbook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.marshalchen.common.demoofui.R;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_flow_artbook_about);
		findViewById(R.id.github_button).setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "https://github.com/Comcast/FreeFlow/";
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);
			}
		});
	}

}
