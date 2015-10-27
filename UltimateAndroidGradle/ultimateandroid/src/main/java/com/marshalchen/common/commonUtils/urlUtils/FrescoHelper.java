package com.marshalchen.common.commonUtils.urlUtils;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;

/**
 *  Fresco support images show effect
 */
public class FrescoHelper {
    Uri uri;
    float sizeRatio = -1.0f;
    ScalingUtils.ScaleType actualScaleType = ScalingUtils.ScaleType.CENTER_CROP;
    PointF actualFocusPoint = null;
    Drawable overlayDrawable = null;
    Drawable placeholderDrawable = null;
    int placeholderRes = 0;
    RoundingParams roundingParams = null;
    boolean autoPlayAnimations = false;
    boolean tapToRetryEnabled = false;
    boolean progressiveRenderingEnabled = false;
    boolean localThumbnailPreviewsEnabled = false;
    boolean autoRotateEnabled = false;
    ResizeOptions resizeOptions = null;

    /**
     * Initialize Fresco
     */
    public static void init(Context context, ImagePipelineConfig config) {
        Fresco.initialize(context, config);
    }

    public FrescoHelper(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri == null");
        }
        this.uri = uri;
    }

    /**
     * Laod image with URI
     * @return
     */
    public static FrescoHelper load(String path) {
        return new FrescoHelper(Uri.parse(path));
    }

    public static FrescoHelper load(int resourceId) {
        return new FrescoHelper(new Uri.Builder().scheme(UriUtil.LOCAL_RESOURCE_SCHEME).path(String.valueOf(resourceId)).build());
    }

    public static FrescoHelper load(File file) {
        return new FrescoHelper(Uri.fromFile(file));
    }

    public static FrescoHelper load(Uri uri) {
        return new FrescoHelper(uri);
    }

    public FrescoHelper setSizeRatio(float ratio) {
        this.sizeRatio = ratio;
        return this;
    }

    /**
     * Set placeholderImage
     * @return
     */
    public FrescoHelper placeholder(int placeholderResId) {
        this.placeholderRes = placeholderResId;
        return this;
    }

    public FrescoHelper placeholder(Drawable placeholderDrawable) {
        this.placeholderDrawable = placeholderDrawable;
        return this;
    }

    /**
     * Center without zooming
     * @return
     */
    public FrescoHelper center() {
        return setScaleType(ScalingUtils.ScaleType.CENTER);
    }

    /**
     * Center with keep zooming and both sides is greater than or equal to show boundaries
     * @return
     */
    public FrescoHelper centerCrop() {
        return setScaleType(ScalingUtils.ScaleType.CENTER_CROP);
    }

    /**
     * specified point with keep zooming and both sides is greater than or equal to show boundaries
     * @return
     */
    public FrescoHelper focusCrop(PointF point) {
        setActualFocusPoint(point);
        return setScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
    }

    public FrescoHelper setActualFocusPoint(PointF actualFocusPoint) {
        this.actualFocusPoint = actualFocusPoint;
        return this;
    }

    /**
     * Center with keep within the border
     * @return
     */
    public FrescoHelper centerInside() {
        return setScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
    }

    /**
     * Center with keep zooming and fully displayed in the picture within the boundary
     * @return
     */
    public FrescoHelper fitCenter() {
        return setScaleType(ScalingUtils.ScaleType.FIT_CENTER);
    }

    //
    /**
     * Top left corner with keep zooming and fully displayed in the picture within the boundary
     * @return
     */
    public FrescoHelper fitStart() {
        return setScaleType(ScalingUtils.ScaleType.FIT_START);
    }

    /**
     * Bottom right corner with keep zooming and fully displayed in the picture within the boundary
     * @return
     */
    public FrescoHelper fitEnd() {
        return setScaleType(ScalingUtils.ScaleType.FIT_END);
    }

    /**
     * Do not save aspect ratio, fill the full display boundary
     * @return
     */
    public FrescoHelper fitXY() {
        return setScaleType(ScalingUtils.ScaleType.FIT_XY);
    }

    public FrescoHelper setScaleType(ScalingUtils.ScaleType actualScaleType) {
        this.actualScaleType = actualScaleType;
        return this;
    }

    /**
     * Circle image
     * @return
     */
    public FrescoHelper circle() {
        return setRoundingParams(new RoundingParams().setRoundAsCircle(true));
    }

    /**
     * Circle image with border
     * @return
     */
    public FrescoHelper circle(int borderWidth, int borderColor) {
        return setRoundingParams(new RoundingParams().setRoundAsCircle(true).setBorder(borderColor, borderWidth));
    }

    /**
     * The elliptical Settings
     * @return
     */
    public FrescoHelper roundedCorner(float radius) {
        return roundedCorner(radius, radius, radius, radius);
    }

    public FrescoHelper roundedCorner(float radius, int borderWidth, int borderColor) {
        return roundedCorner(radius, radius, radius, radius, borderWidth, borderColor);
    }

    public FrescoHelper roundedCorner(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        return setRoundingParams(new RoundingParams().setRoundAsCircle(false).setCornersRadii(topLeft, topRight, bottomRight, bottomLeft));
    }

    public FrescoHelper roundedCorner(float topLeft, float topRight, float bottomRight, float bottomLeft, int borderWidth, int borderColor) {
        return setRoundingParams(new RoundingParams().setRoundAsCircle(false).setCornersRadii(topLeft, topRight, bottomRight, bottomLeft).setBorder(borderColor, borderWidth));
    }

    public FrescoHelper setRoundingParams(RoundingParams roundingParams) {
        this.roundingParams = roundingParams;
        return this;
    }

    /**
     * Set overlayImage
     * @return
     */
    public FrescoHelper overlay(Drawable overlayDrawable) {
        this.overlayDrawable = overlayDrawable;
        return this;
    }

    /**
     * Gif auto play
     * @return
     */
    public FrescoHelper autoPlayAnimations() {
        this.autoPlayAnimations = true;
        return this;
    }

    /**
     * Set click reload image if iamge request failure
     * @return
     */
    public FrescoHelper tapToRetryEnabled() {
        this.tapToRetryEnabled = true;
        return this;
    }

    /**
     * Set image progressiveRendering or not
     * @return
     */
    public FrescoHelper progressiveRenderingEnabled() {
        this.progressiveRenderingEnabled = true;
        return this;
    }

    /**
     * Thumbnail Preview.
     * This feature support local URI only and iamge must be JPEG format
     * @return
     */
    public FrescoHelper localThumbnailPreviewsEnabled() {
        this.localThumbnailPreviewsEnabled = true;
        return this;
    }

    /**
     * Modify the image size before decode.
     * This scaling method, bigger than Android built-in zoom range.
     * Android camera pictures generated average size is very big, need to adjust the size to be displayed.
     * At present, support only JPEG images but fortunately most of the Android camera is JPEG images.
     * If you want to modify the image size, create ImageRequest, provide a ResizeOptions
     * @return
     */
    public FrescoHelper resizeOptions(int width, int height) {
        this.resizeOptions = new ResizeOptions(width, height);
        return this;
    }

    /**
     * Auto rotate
     * @return
     */
    public FrescoHelper autoRotateEnabled() {
        this.autoRotateEnabled = true;
        return this;
    }

    public void into(SimpleDraweeView view, ControllerListener<Object> callback) {
        into(view, callback, null);
    }

    public void into(SimpleDraweeView view, Postprocessor postprocessor) {
        into(view, null, postprocessor);
    }

    public void into(SimpleDraweeView view) {
        into(view, null, null);
    }

    public void into(SimpleDraweeView view, ControllerListener<Object> callback, Postprocessor postprocessor) {

        GenericDraweeHierarchy hierarchy = view.getHierarchy();

        boolean isHierarchyChange = false;
        if (actualScaleType != ScalingUtils.ScaleType.CENTER_CROP) {
            hierarchy.setActualImageScaleType(actualScaleType);
            isHierarchyChange = true;
        }

        if (actualFocusPoint != null) {
            hierarchy.setActualImageFocusPoint(actualFocusPoint);
            isHierarchyChange = true;
        }

        if (overlayDrawable != null) {
            hierarchy.setControllerOverlay(overlayDrawable);
            isHierarchyChange = true;
        }

        if (placeholderDrawable != null) {
            hierarchy.setPlaceholderImage(placeholderDrawable);
            isHierarchyChange = true;
        }

        if (placeholderRes != 0) {
            hierarchy.setPlaceholderImage(placeholderRes);
            isHierarchyChange = true;
        }

        if (roundingParams != null) {
            hierarchy.setRoundingParams(roundingParams);
            isHierarchyChange = true;
        }

        ImageRequest request = null;
        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        boolean isRequestChange = false;
        if (autoRotateEnabled) {
            requestBuilder.setAutoRotateEnabled(true);
            isRequestChange = true;
        }

        if (localThumbnailPreviewsEnabled) {
            requestBuilder.setLocalThumbnailPreviewsEnabled(true);
            isRequestChange = true;
        }

        if (postprocessor != null) {
            requestBuilder.setPostprocessor(postprocessor);
            isRequestChange = true;
        }

        if (progressiveRenderingEnabled) {
            requestBuilder.setProgressiveRenderingEnabled(true);
            isRequestChange = true;
        }

        if (resizeOptions != null) {
            requestBuilder.setResizeOptions(resizeOptions);
            isRequestChange = true;
        }

        if (isRequestChange) {
            request = requestBuilder.build();
        }

        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();
        DraweeController controller = null;
        boolean isControllerChange = false;
        if (callback != null) {
            controllerBuilder.setControllerListener(callback);
            isControllerChange = true;
        }

        if (autoPlayAnimations) {
            controllerBuilder.setAutoPlayAnimations(true);
            isControllerChange = true;
        }

        if (tapToRetryEnabled) {
            controllerBuilder.setTapToRetryEnabled(true);
            isControllerChange = true;
        }

        if (request != null) {
            controllerBuilder.setImageRequest(request);
            isControllerChange = true;
        } else if (request == null && isControllerChange) {
            controllerBuilder.setUri(uri);
        }

        if (isControllerChange) {
            controllerBuilder.setOldController(view.getController());
            controller = controllerBuilder.build();
        }

        if (sizeRatio > 0) {
            view.setAspectRatio(sizeRatio);
        }

        if (isHierarchyChange) {
            view.setHierarchy(hierarchy);
        }

        if (controller != null) {
            view.setController(controller);
        } else {
            view.setImageURI(uri);
        }


    }
}
