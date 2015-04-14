package com.marshalchen.common.uimodule.blurdialogfragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

/**
 * Encapsulate dialog behavior with blur effect for app using {@link android.app.DialogFragment}.
 * <p/>
 * All the screen behind the dialog will be blurred except the action bar.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BlurDialogFragment extends DialogFragment {

    /**
     * Bundle key used to start the blur dialog with a given scale factor (float).
     */
    public static final String BUNDLE_KEY_DOWN_SCALE_FACTOR = "bundle_key_down_scale_factor";

    /**
     * Bundle key used to start the blur dialog with a given blur radius (int).
     */
    public static final String BUNDLE_KEY_BLUR_RADIUS = "bundle_key_blur_radius";

    /**
     * Log cat
     */
    private static final String TAG = BlurDialogFragment.class.getSimpleName();

    /**
     * Engine used to blur.
     */
    private BlurDialogEngine mBlurEngine;

    /**
     *
     */
    private boolean mDebugEnable;

    /**
     * default constructor as needed
     */
    public BlurDialogFragment() {
        mDebugEnable = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBlurEngine = new BlurDialogEngine(getActivity());
        mBlurEngine.debug(mDebugEnable);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(BUNDLE_KEY_BLUR_RADIUS)) {
                mBlurEngine.setBlurRadius(args.getInt(BUNDLE_KEY_BLUR_RADIUS));
            }
            if (args.containsKey(BUNDLE_KEY_DOWN_SCALE_FACTOR)) {
                mBlurEngine.setDownScaleFactor(args.getFloat(BUNDLE_KEY_DOWN_SCALE_FACTOR));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBlurEngine.onResume(getRetainInstance());
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mBlurEngine.onDismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBlurEngine.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    /**
     * Enable or disable debug mode.
     *
     * @param debugEnable true if debug mode should be enabled.
     */
    public void debug(boolean debugEnable) {
        mDebugEnable = debugEnable;
    }

    /**
     * Set the down scale factor used by the {@link BlurDialogEngine}
     *
     * @param factor down scaled factor used to reduce the size of the source image.
     *               Range :  ]0,infinity)
     */
    public void setDownScaleFactor(float factor) {
        if (factor > 0) {
            mBlurEngine.setDownScaleFactor(factor);
        }
    }

    /**
     * Set the blur radius used by the {@link BlurDialogEngine}
     *
     * @param radius down scaled factor used to reduce the size of the source image.
     *               Range :  [1,infinity)
     */
    public void setBlurRadius(int radius) {
        if (radius > 0) {
            mBlurEngine.setBlurRadius(radius);
        }
    }
}
