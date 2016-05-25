/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.triangle;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.marshalchen.common.uimodule.R;

/**
 * 转盘
 * 
 * @author Administrator
 * 
 */
public class RotaryView extends View {

	private float min;
	private float[] humidity;
	private float[] Sweep;
	private float[] SWEEP_INC = { 0, 0, 0, 0, 0 };
	public Bitmap bitmapScale;// 刻度

	private float dp;

	private final int[] ARC_COLORS = new int[] { 0xa03cbeff, 0xa0ff5e7d,
			0xa04cd964, 0xa0ffc71e, 0xa0ff6f2f };

	private RectF rect, rectf;
	private Paint paint;
	private Paint paintWhite;
	private Paint bitmapRefreshPaint;
	private int angleWhite;
	private boolean arcBool = false;

	private int discStart = 0;// 角度起点

	private float brWidth, brHeight, scale;
	private float WidthCenter, HeightCenter;
	private RotatingEndListener rotatingEndListener = null;

	public RotaryView(Context context, float[] humidity) {
		super(context);
		setAngle(humidity);
		init();
	}

	public void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		paintWhite = new Paint();
		paintWhite.setAntiAlias(true);
		paintWhite.setColor(0xffffffff);

		paintWhite.setStyle(Paint.Style.FILL_AND_STROKE);

		bitmapRefreshPaint = new Paint();
		bitmapRefreshPaint.setAntiAlias(true);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		this.getViewTreeObserver().addOnPreDrawListener(// 绘制完毕
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						Init();
						getViewTreeObserver().removeOnPreDrawListener(this);
						return false;
					}
				});
	}

	public void Init() {
		dp = getResources().getDimension(R.dimen.triangle_dp);

		bitmapScale = BitmapFactory.decodeResource(getResources(),
				R.drawable.triangle_icon_round_calibration);
		brWidth = bitmapScale.getWidth();
		brHeight = bitmapScale.getHeight();

		WidthCenter = getWidth() / 2;
		HeightCenter = getHeight() / 2;

		zoom(0f);
		rectf = new RectF();
		rectf.set(dp * 0.1f, dp * 0.1f, getWidth() - dp * 0.1f, getHeight()
				- dp * 0.1f);

	}

	public void setAngle(float[] humidity) {
		this.humidity = humidity;
		float[] temp = new float[humidity.length-1];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = humidity[i + 1];
		}
		Sweep = temp;
		min = getMin(temp);
		for (int i = 0; i < SWEEP_INC.length; i++) {
			SWEEP_INC[i] = humidity[i + 1] / min;
		}
	}

	protected void onDraw(Canvas c) {
		drawArc(c);
		drawPointer(c);
		drawArcWhe(c);
	}

	public void drawArc(Canvas canvas) {
		float start = humidity[0];
		for (int i = 1; i < humidity.length; i++) {
			paint.setColor(ARC_COLORS[i - 1]);
			canvas.drawArc(rect, start - discStart, Sweep[i - 1], true, paint);
			start += humidity[i];
			if (Sweep[i - 1] < humidity[i]) {
				Sweep[i - 1] += SWEEP_INC[i - 1];
			}
		}
	}

	public void drawPointer(Canvas c) {
		Matrix matrix = new Matrix();
		// 设置缩放
		matrix.postScale(scale, scale);
		// 开始转
		matrix.preRotate((float) -discStart);
		// 转轴还原
		matrix.preTranslate(-(float) brWidth / 2, -(float) brHeight / 2);
		// 将位置送到view的中心
		matrix.postTranslate(WidthCenter, HeightCenter);
		// 绘制图片
		c.drawBitmap(bitmapScale, matrix, bitmapRefreshPaint);

	}

	public void drawArcWhe(Canvas c) {
		if (arcBool) {
			c.drawArc(rectf, -90 + angleWhite, 360 - angleWhite, true,
					paintWhite);
		}
	}

	public float getMin(float[] humidity) {
		float min = humidity[0];
		for (int i = 1; i < humidity.length; i++) {
			if (humidity[i] < min) {
				min = humidity[i];
			}
		}
		return min;
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public boolean isArcBool() {
		return arcBool;
	}

	public void setArcBool(boolean arcBool) {
		this.arcBool = arcBool;
	}

	public void zoom(float ratio) {
		float than = ratio + dp * 0.3f;
		rect = new RectF();
		rect.set(than, than, getWidth() - than, getHeight() - than);
		scale = (getWidth() - than * 2) / brWidth;
		invalidate();
	}

	public void rotatingStart(float angle) {
		discStart = 0;
		postInvalidate();
		new RotationArc(angle);
	}

	public void setTargetAngle(float weight) {
		discStart = (int) (weight * 2.4f);
		invalidate();
	}

	/**
	 * 转盘转动动画
	 * 
	 * @author Administrator
	 * 
	 */
	class RotationArc implements Runnable {
		// 手指离开屏幕返回动画
		private Thread thread;
		private float targetAngle;

		public RotationArc(float angle) {
			targetAngle = (int) (angle * 2.4f);
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(4);
					discStart++;
					postInvalidate();
					if (discStart >= targetAngle) {
						// 旋转结束发送消息并回调
						Message message = new Message();
						rotatingEnd.sendMessage(message);
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	Handler rotatingEnd = new Handler() {
		public void handleMessage(Message msg) {
			if (rotatingEndListener != null) {
				rotatingEndListener.onRotatingEnd();
			}
			super.handleMessage(msg);
		}
	};

	public void RotatingShowStart() {
		new RotatingShow();
	}

	/**
	 * 旋转显示动画
	 * 
	 * @author Administrator
	 * 
	 */
	class RotatingShow implements Runnable {
		private Thread thread;

		public RotatingShow() {
			angleWhite = 0;
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(6);
					if (angleWhite == 360) {
						break;
					}
					angleWhite += 5;
					postInvalidate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setRotatingEndListener(RotatingEndListener rotatingEndListener) {
		this.rotatingEndListener = rotatingEndListener;
	}

	public interface RotatingEndListener {
		// 旋转动态监听
		public void onRotatingEnd();
	}
}
