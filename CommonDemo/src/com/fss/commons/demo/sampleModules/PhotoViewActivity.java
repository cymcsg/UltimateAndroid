package com.fss.commons.demo.sampleModules;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.fss.commons.commonUtils.basicUtils.HandlerUtils;
import com.fss.commons.ui.HomeasUpActionbarActivity;
import com.fss.commons.uiModule.photoview.PhotoViewAttacher;
import com.fss.commons.demo.R;

/**
 * Created by cym on 14-6-26.
 */
public class PhotoViewActivity extends HomeasUpActionbarActivity {
    //    @InjectView(R.id.photoView)
//    PhotoView photoView;
    @InjectView(R.id.photoViewImageView)
    ImageView photoViewImageView;
    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view_activity);
        ButterKnife.inject(this);
        // Set the Drawable displayed
        Drawable bitmap = getResources().getDrawable(R.drawable.test_back);
        photoViewImageView.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        photoViewAttacher = new PhotoViewAttacher(photoViewImageView);
        HandlerUtils.sendMessageHandlerDelay(changeImageHandler, 0, 2000);

    }

    Handler changeImageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            photoViewImageView.setImageResource(R.drawable.test);
            // If you later call mImageView.setImageDrawable/setImageBitmap/setImageResource/etc then you just need to call
            photoViewAttacher.update();
        }
    };
}
