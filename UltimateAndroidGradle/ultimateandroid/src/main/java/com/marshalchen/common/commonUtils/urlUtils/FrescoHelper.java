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
 *
 * Created by echo on 2015/10/24.
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
     * Fresco初始化
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
     * 图片载入
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
     * 设置占位图
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
     * 居中，无缩放
     * @return
     */
    public FrescoHelper center() {
        return setScaleType(ScalingUtils.ScaleType.CENTER);
    }

    /**
     * Center with keep zooming and both sides is greater than or equal to show boundaries
     * 保持宽高比缩小或放大，使得两边都大于或等于显示边界。居中显示
     * @return
     */
    public FrescoHelper centerCrop() {
        return setScaleType(ScalingUtils.ScaleType.CENTER_CROP);
    }

    /**
     * specified point with keep zooming and both sides is greater than or equal to show boundaries
     * 同centerCrop, 但居中点不是中点，而是指定的某个点
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
     * 使两边都在显示边界内，居中显示。
     * 如果图尺寸大于显示边界，则保持长宽比缩小图片
     * @return
     */
    public FrescoHelper centerInside() {
        return setScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
    }

    /**
     * Center with keep zooming and fully displayed in the picture within the boundary
     * 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内。居中显示
     * @return
     */
    public FrescoHelper fitCenter() {
        return setScaleType(ScalingUtils.ScaleType.FIT_CENTER);
    }

    //
    /**
     * Top left corner with keep zooming and fully displayed in the picture within the boundary
     * 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内。但不居中，和显示边界左上对齐
     * @return
     */
    public FrescoHelper fitStart() {
        return setScaleType(ScalingUtils.ScaleType.FIT_START);
    }

    /**
     * Bottom right corner with keep zooming and fully displayed in the picture within the boundary
     * 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内。但不居中，和显示边界右下对齐
     * @return
     */
    public FrescoHelper fitEnd() {
        return setScaleType(ScalingUtils.ScaleType.FIT_END);
    }

    /**
     * Do not save aspect ratio, fill the full display boundary
     * 不保存宽高比，填充满显示边界
     * @return
     */
    public FrescoHelper fitXY() {
        return setScaleType(ScalingUtils.ScaleType.FIT_XY);
    }

//    //如要使用tile mode显示, 需要设置为none
//    public RFresco tile() {
//        return setScaleType(ScalingUtils.ScaleType.fromString("none"));
//    }

    public FrescoHelper setScaleType(ScalingUtils.ScaleType actualScaleType) {
        this.actualScaleType = actualScaleType;
        return this;
    }

    /**
     * Circle image
     * 圆图
     * @return
     */
    public FrescoHelper circle() {
        return setRoundingParams(new RoundingParams().setRoundAsCircle(true));
    }

    /**
     * Circle image with border
     * 圆图加边框
     * @return
     */
    public FrescoHelper circle(int borderWidth, int borderColor) {
        return setRoundingParams(new RoundingParams().setRoundAsCircle(true).setBorder(borderColor, borderWidth));
    }

    /**
     * The elliptical Settings
     * 椭圆各项设置
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
     * 设置叠加图
     * @return
     */
    public FrescoHelper overlay(Drawable overlayDrawable) {
        this.overlayDrawable = overlayDrawable;
        return this;
    }

    /**
     * Gif auto play
     * gif图是否自动播放
     * @return
     */
    public FrescoHelper autoPlayAnimations() {
        this.autoPlayAnimations = true;
        return this;
    }

    /**
     * Set click reload image if iamge request failure
     * 图片请求失败后，点击图片是否重新加载
     * @return
     */
    public FrescoHelper tapToRetryEnabled() {
        this.tapToRetryEnabled = true;
        return this;
    }

    /**
     * Set image progressiveRendering or not
     * 是否支持图片渐进显示渲染（仅针对jpg）
     * @return
     */
    public FrescoHelper progressiveRenderingEnabled() {
        this.progressiveRenderingEnabled = true;
        return this;
    }

    /**
     * Thumbnail Preview.
     * This feature support local URI only and iamge must be JPEG format
     * 缩略图预览
     * 本功能仅支持本地URI，并且是JPEG图片格式
     * 如果本地JPEG图，有EXIF的缩略图，image pipeline 会立刻返回一个缩略图。完整的清晰大图，在decode完之后再显示。
     * @return
     */
    public FrescoHelper localThumbnailPreviewsEnabled() {
        this.localThumbnailPreviewsEnabled = true;
        return this;
    }

    /*
        修改图片尺寸
        调整大小并不是修改原来的文件，而是在解码之前，在native内存中修改。

        这个缩放方法，比Android内置的缩放范围更大。Android相机生成的照片一般尺寸都很大，需要调整大小之后才能被显示。

        目前，仅仅支持JPEG格式的图片，同时，大部分的Android系统相机图片都是JPEG的。

        如果要修改图片尺寸，创建ImageRequest时，提供一个 ResizeOptions:
     */
    public FrescoHelper resizeOptions(int width, int height) {
        this.resizeOptions = new ResizeOptions(width, height);
        return this;
    }

    /*
        自动旋转
        如果看到的图片是侧着的，用户是难受的。许多设备会在JPEG文件的metadata中记录下照片的方向。如果你想图片呈现的方向和设备屏幕的方向一致，你可以简单地这样做到:
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
