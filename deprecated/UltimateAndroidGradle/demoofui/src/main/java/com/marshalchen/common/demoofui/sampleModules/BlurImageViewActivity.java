package com.marshalchen.common.demoofui.sampleModules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.marshalchen.common.demoofui.R;
import com.wingjay.blurimageviewlib.BlurImageView;


public class BlurImageViewActivity extends AppCompatActivity {

//    @InjectView(R.id.blur_factor)
//    EditText blurFactorEditText;


    Button fastBlurBtn;

    BlurImageView blurImageView;

    BlurImageView fullBlurImageView;

    boolean alreadyCompelete = false;

    int[] mediumSmRes = {
            R.drawable.test,
            R.drawable.test_back,
            R.drawable.test_back1,
            R.drawable.test_back2
    };

    int[] mediumNmRes = {
            R.drawable.test,
            R.drawable.test_back,
            R.drawable.test_back1,
            R.drawable.test_back2
    };

    String[] mediumSmUrl = {
            "https://cdn-images-1.medium.com/freeze/max/60/1*cDmQ2XlMGowTZNIf4oOHjw.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/60/1*hBp9i_LZHGwKfq7plvjWxQ.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/30/1*yd_YDN3dVyrSp_o7YHOKPg.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/60/1*hMQ9_nBW3LOHCk3rQSRSbw.jpeg?q=20"
    };

    String[] mediumNmUrl = {
            "https://cdn-images-1.medium.com/max/1600/1*cDmQ2XlMGowTZNIf4oOHjw.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*hBp9i_LZHGwKfq7plvjWxQ.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*yd_YDN3dVyrSp_o7YHOKPg.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*hMQ9_nBW3LOHCk3rQSRSbw.jpeg"
    };

    int currentIndex = 0;

    int getResIndex() {
        if (currentIndex > 3) {
            currentIndex = currentIndex - 4;
        }
        return currentIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_image_view_activity_main);

        fastBlurBtn = (Button) findViewById(R.id.fast_blur_btn);
        blurImageView = (BlurImageView) findViewById(R.id.blur_image_view);
        fullBlurImageView = (BlurImageView) findViewById(R.id.full_blur_image_view);
        fastBlurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alreadyCompelete) {
                    int blurFactor = getBlurFactor();
                    fullBlurImageView.setBlurFactor(blurFactor);
                    fullBlurImageView.setFullImageByUrl(mediumSmUrl[getResIndex()], mediumNmUrl[getResIndex()]);

                    blurImageView.setBlurFactor(blurFactor);
                    blurImageView.setBlurImageByUrl(mediumSmUrl[getResIndex()]);
                    alreadyCompelete = true;
                } else {
                    blurImageView.clear();
                    fullBlurImageView.clear();
                    currentIndex++;
                    alreadyCompelete = false;
                }
            }
        });
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


    }

//    @OnClick(R.id.fast_blur_btn)
//    void doFastBlur() {
//        if (!alreadyCompelete) {
//            int blurFactor = getBlurFactor();
//            fullBlurImageView.setBlurFactor(blurFactor);
//            fullBlurImageView.setFullImageByUrl(mediumSmUrl[getResIndex()], mediumNmUrl[getResIndex()]);
//
//            blurImageView.setBlurFactor(blurFactor);
//            blurImageView.setBlurImageByUrl(mediumSmUrl[getResIndex()]);
//            alreadyCompelete = true;
//        } else {
//            blurImageView.clear();
//            fullBlurImageView.clear();
//            currentIndex++;
//            alreadyCompelete = false;
//        }
//    }

    int getBlurFactor() {
//        if (TextUtils.isEmpty(blurFactorEditText.getText())) {
//            return BlurImageView.DEFAULT_BLUR_FACTOR;
//        }
//        return Integer.parseInt(blurFactorEditText.getText().toString());

        return BlurImageView.DEFAULT_BLUR_FACTOR;
    }

    @Override
    protected void onDestroy() {
        blurImageView.cancelImageLoadForSafty();
        fullBlurImageView.cancelImageLoadForSafty();
        super.onDestroy();
    }

}
