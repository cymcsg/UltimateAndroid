package com.marshalchen.common.uimodule.square_progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.marshalchen.common.uimodule.square_progressbar.utils.CalculationUtil;
import com.marshalchen.common.uimodule.square_progressbar.utils.PercentStyle;

import java.text.DecimalFormat;

public class SquareProgressView extends View {

	private double progress;
	private final Paint progressBarPaint;
	private final Paint outlinePaint;
	private final Paint textPaint;

	private float widthInDp = 10;
	private float strokewidth = 0;
	private Canvas canvas;

	private boolean outline = false;
	private boolean startline = false;
	private boolean showProgress = false;

	private PercentStyle percentSettings = new PercentStyle(Align.CENTER, 150,
			true);
	private boolean clearOnHundred = false;

	public SquareProgressView(Context context) {
		super(context);
		progressBarPaint = new Paint();
		progressBarPaint.setColor(context.getResources().getColor(
				android.R.color.holo_green_dark));
		progressBarPaint.setStrokeWidth(CalculationUtil.convertDpToPx(
				widthInDp, getContext()));
		progressBarPaint.setAntiAlias(true);
		progressBarPaint.setStyle(Style.STROKE);

		outlinePaint = new Paint();
		outlinePaint.setColor(context.getResources().getColor(
				android.R.color.black));
		outlinePaint.setStrokeWidth(1);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStyle(Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(context.getResources().getColor(
				android.R.color.black));
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Style.STROKE);

	}

	public SquareProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		progressBarPaint = new Paint();
		progressBarPaint.setColor(context.getResources().getColor(
				android.R.color.holo_green_dark));
		progressBarPaint.setStrokeWidth(CalculationUtil.convertDpToPx(
				widthInDp, getContext()));
		progressBarPaint.setAntiAlias(true);
		progressBarPaint.setStyle(Style.STROKE);

		outlinePaint = new Paint();
		outlinePaint.setColor(context.getResources().getColor(
				android.R.color.black));
		outlinePaint.setStrokeWidth(1);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStyle(Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(context.getResources().getColor(
				android.R.color.black));
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Style.STROKE);
	}

	public SquareProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressBarPaint = new Paint();
		progressBarPaint.setColor(context.getResources().getColor(
				android.R.color.holo_green_dark));
		progressBarPaint.setStrokeWidth(CalculationUtil.convertDpToPx(
				widthInDp, getContext()));
		progressBarPaint.setAntiAlias(true);
		progressBarPaint.setStyle(Style.STROKE);

		outlinePaint = new Paint();
		outlinePaint.setColor(context.getResources().getColor(
				android.R.color.black));
		outlinePaint.setStrokeWidth(1);
		outlinePaint.setAntiAlias(true);
		outlinePaint.setStyle(Style.STROKE);

		textPaint = new Paint();
		textPaint.setColor(context.getResources().getColor(
				android.R.color.black));
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;
		super.onDraw(canvas);
		strokewidth = CalculationUtil.convertDpToPx(widthInDp, getContext());
		float scope = canvas.getWidth() + canvas.getHeight()
				+ canvas.getHeight() + canvas.getWidth();
		float percent = (scope / 100) * Float.valueOf(String.valueOf(progress));
		float halfOfTheImage = canvas.getWidth() / 2;

		if (outline) {
			drawOutline();
		}

		if (isStartline()) {
			drawStartline();
		}

		if (showProgress) {
			drawPercent(percentSettings);
		}

		if (clearOnHundred && progress == 100.0) {
			return;
		}

		Path path = new Path();
		if (percent > halfOfTheImage) {
			paintFirstHalfOfTheTop(canvas);
			float second = percent - halfOfTheImage;

			if (second > canvas.getHeight()) {
				paintRightSide(canvas);
				float third = second - canvas.getHeight();

				if (third > canvas.getWidth()) {
					paintBottomSide(canvas);
					float forth = third - canvas.getWidth();

					if (forth > canvas.getHeight()) {
						paintLeftSide(canvas);
						float fifth = forth - canvas.getHeight();

						if (fifth == halfOfTheImage) {
							paintSecondHalfOfTheTop(canvas);
						} else {
							path.moveTo(strokewidth, (strokewidth / 2));
							path.lineTo(strokewidth + fifth, (strokewidth / 2));
							canvas.drawPath(path, progressBarPaint);
						}
					} else {
						path.moveTo((strokewidth / 2), canvas.getHeight()
								- strokewidth);
						path.lineTo((strokewidth / 2), canvas.getHeight()
								- forth);
						canvas.drawPath(path, progressBarPaint);
					}

				} else {
					path.moveTo(canvas.getWidth() - strokewidth,
							canvas.getHeight() - (strokewidth / 2));
					path.lineTo(canvas.getWidth() - third, canvas.getHeight()
							- (strokewidth / 2));
					canvas.drawPath(path, progressBarPaint);
				}
			} else {
				path.moveTo(canvas.getWidth() - (strokewidth / 2), strokewidth);
				path.lineTo(canvas.getWidth() - (strokewidth / 2), strokewidth
						+ second);
				canvas.drawPath(path, progressBarPaint);
			}

		} else {
			path.moveTo(halfOfTheImage, strokewidth / 2);
			path.lineTo(halfOfTheImage + percent, strokewidth / 2);
			canvas.drawPath(path, progressBarPaint);
		}

	}

	private void drawStartline() {
		Path outlinePath = new Path();
		outlinePath.moveTo(canvas.getWidth() / 2, 0);
		outlinePath.lineTo(canvas.getWidth() / 2, strokewidth);
		canvas.drawPath(outlinePath, outlinePaint);
	}

	private void drawOutline() {
		Path outlinePath = new Path();
		outlinePath.moveTo(0, 0);
		outlinePath.lineTo(canvas.getWidth(), 0);
		outlinePath.lineTo(canvas.getWidth(), canvas.getHeight());
		outlinePath.lineTo(0, canvas.getHeight());
		outlinePath.lineTo(0, 0);
		canvas.drawPath(outlinePath, outlinePaint);
	}

	public void paintFirstHalfOfTheTop(Canvas canvas) {
		Path path = new Path();
		path.moveTo(canvas.getWidth() / 2, strokewidth / 2);
		path.lineTo(canvas.getWidth() + strokewidth, strokewidth / 2);
		canvas.drawPath(path, progressBarPaint);
	}

	public void paintRightSide(Canvas canvas) {
		Path path = new Path();
		path.moveTo(canvas.getWidth() - (strokewidth / 2), strokewidth);
		path.lineTo(canvas.getWidth() - (strokewidth / 2), canvas.getHeight());
		canvas.drawPath(path, progressBarPaint);
	}

	public void paintBottomSide(Canvas canvas) {
		Path path = new Path();
		path.moveTo(canvas.getWidth() - strokewidth, canvas.getHeight()
				- (strokewidth / 2));
		path.lineTo(0, canvas.getHeight() - (strokewidth / 2));
		canvas.drawPath(path, progressBarPaint);
	}

	public void paintLeftSide(Canvas canvas) {
		Path path = new Path();
		path.moveTo((strokewidth / 2), canvas.getHeight() - strokewidth);
		path.lineTo((strokewidth / 2), 0);
		canvas.drawPath(path, progressBarPaint);
	}

	public void paintSecondHalfOfTheTop(Canvas canvas) {
		Path path = new Path();
		path.moveTo(strokewidth, (strokewidth / 2));
		path.lineTo(canvas.getWidth() / 2, (strokewidth / 2));
		canvas.drawPath(path, progressBarPaint);
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
		this.invalidate();
	}

	public void setColor(int color) {
		progressBarPaint.setColor(color);
		this.invalidate();
	}

	/**
	 * @return the border
	 */
	public float getWidthInDp() {
		return widthInDp;
	}

	/**
	 * @return the border
	 */
	public void setWidthInDp(int width) {
		this.widthInDp = width;
		progressBarPaint.setStrokeWidth(CalculationUtil.convertDpToPx(
				widthInDp, getContext()));
		this.invalidate();
	}

	public boolean isOutline() {
		return outline;
	}

	public void setOutline(boolean outline) {
		this.outline = outline;
		this.invalidate();
	}

	public boolean isStartline() {
		return startline;
	}

	public void setStartline(boolean startline) {
		this.startline = startline;
		this.invalidate();
	}

	private void drawPercent(PercentStyle setting) {
		textPaint.setTextAlign(setting.getAlign());
		if (setting.getTextSize() == 0) {
			textPaint.setTextSize((canvas.getHeight() / 10) * 4);
		} else {
			textPaint.setTextSize(setting.getTextSize());
		}

		String percentString = new DecimalFormat("###").format(getProgress());
		if (setting.isPercentSign()) {
			percentString = percentString + percentSettings.getCustomText();
		}

		textPaint.setColor(percentSettings.getTextColor());

		canvas.drawText(
				percentString,
				canvas.getWidth() / 2,
				(int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint
						.ascent()) / 2)), textPaint);
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
		this.invalidate();
	}

	public void setPercentStyle(PercentStyle percentSettings) {
		this.percentSettings = percentSettings;
		this.invalidate();
	}

	public PercentStyle getPercentStyle() {
		return percentSettings;
	}

	public void setClearOnHundred(boolean clearOnHundred) {
		this.clearOnHundred = clearOnHundred;
		this.invalidate();
	}

	public boolean isClearOnHundred() {
		return clearOnHundred;
	}

}
