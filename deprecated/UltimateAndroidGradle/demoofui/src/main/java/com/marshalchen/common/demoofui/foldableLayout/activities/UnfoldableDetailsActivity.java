package com.marshalchen.common.demoofui.foldableLayout.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.marshalchen.common.uimodule.foldablelayout.UnfoldableView;
import com.marshalchen.common.uimodule.foldablelayout.shading.GlanceFoldShading;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.foldableLayout.items.Painting;
import com.marshalchen.common.demoofui.foldableLayout.items.PaintingsAdapter;


public class UnfoldableDetailsActivity extends BaseActivity {

    private ListView mListView;
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foldable_activity_unfoldable_details);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new PaintingsAdapter(this));

        mListTouchInterceptor = (View) findViewById(R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = findViewById(R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView = (UnfoldableView) findViewById(R.id.unfoldable_view);

        Bitmap glance = ((BitmapDrawable) getResources().getDrawable(R.drawable.folder_unfold_glance)).getBitmap();
        mUnfoldableView.setFoldShading(new GlanceFoldShading(this, glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        ImageView image = (ImageView) mDetailsLayout.findViewById(R.id.details_image);
        TextView title = (TextView) mDetailsLayout.findViewById(R.id.details_title);
        TextView description = (TextView) mDetailsLayout.findViewById(R.id.details_text);
        image.setImageResource(R.drawable.test);
        // Picasso.with(this).load(painting.getImageId()).into(image);
        Logs.d("isnull   " + (painting != null) + "   " + (title != null));
//        title.setText(painting.getTitle());


        // description.setText(painting.getTitle());

        mUnfoldableView.unfold(coverView, mDetailsLayout);
        
    }

}
