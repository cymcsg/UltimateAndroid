/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.triangle;

import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.marshalchen.common.uimodule.R;

/**
 * 人形三角形
 * 
 *
 * 
 */
public class TriangleView extends View {
	Bitmap bitmap, defaults, raiseYourhand, toLetGo; // 默认，举手，放手
	Paint paint, paint_text;
	RectF rectF;
	float dp;
	float widthCentre;
	float heightCentre;
	int gender;
	int ratio;
	int than;
	int tag[];
	final int lenght = 6;//控制行数 大于0

	/**
	 * 
	 * @param context
	 * @param gender
	 *            性别 0代表女 1代表男
	 * @param than
	 *            百分比
	 */
	public TriangleView(Context context, int gender, int than) {
		super(context);
		tag = get(lenght);
		this.gender = gender;
		ratio = than;
		this.than = (int) ((100.0f - than) / (100.0f / tag[lenght - 1]));
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		this.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						init();
						getViewTreeObserver().removeOnPreDrawListener(this);
						return false;
					}
				});
	}

	public void init() {
		dp = getResources().getDimension(R.dimen.triangle_dp);
		paint = new Paint();

		paint_text = new Paint();
		paint_text.setAntiAlias(true);
		paint_text.setColor(0xffffffff);
		paint_text.setTextSize(dp * 1.5f);
		paint_text.setStrokeWidth(dp * 0.15f);
		paint_text.setTextAlign(Align.CENTER);
		paint_text.setStyle(Style.FILL);
		rectF = new RectF();

		widthCentre = getWidth() / 2;
		heightCentre = getHeight() / 3;

		if (gender == 0) {
			defaults = BitmapFactory.decodeResource(getResources(),
					R.drawable.triangle_woman_default);
			raiseYourhand = BitmapFactory.decodeResource(getResources(),
					R.drawable.triangle_woman_for);
			toLetGo = BitmapFactory.decodeResource(getResources(),
					R.drawable.triangle_woman_relinquish);
		} else {
			defaults = BitmapFactory.decodeResource(getResources(),
					R.drawable.triangle_man_default);
			raiseYourhand = BitmapFactory.decodeResource(getResources(),
					R.drawable.triangle_man_for);
			toLetGo = BitmapFactory.decodeResource(getResources(),
					R.drawable.triangle_man_relinquish);
		}
	}

	public int[] get(int lenght) { // 根据三角形规律得到每行最后一个图标的数字，
		int[] tag = new int[lenght];
		tag[0] = 0;
		for (int i = 1; i < tag.length; i++) {
			tag[i] = tag[i - 1] + i;// 规律为后面的数减去前面的数字可以得到 1.2.3...
		}
		return tag;
	}

	protected void onDraw(Canvas c) {
		super.onDraw(c);
		drawTriangle(c);
	}

	public void drawTriangle(Canvas c) {
		c.drawText("你超过了全国 " + ratio + "%的人", getWidth() / 2, dp * 2.5f,
				paint_text);
		float bmpsize = dp * 1.7f; // 图片大小
		heightCentre = getHeight() / 5; // 初始y位置
		widthCentre = getWidth() / 2 + bmpsize;// 初始x位置

		float x = 0;
		float y = 0;
		float offsetX = dp * 3.5f; // x 偏移量
		float offsetY = dp * 3.5f;// y 偏移量
		for (int i = 0; i < tag[lenght-1]; i++) {
			for (int j = 0; j < tag.length; j++) {
				if (i == tag[j]) {
					y = j * offsetY;// 重置y
					x = x - (j * offsetX + offsetX / 2);// 重置x
					break;
				}
			}
			rectF.set(widthCentre - bmpsize + x, heightCentre - bmpsize + y,
					widthCentre + bmpsize + x, heightCentre + bmpsize + y);
			if (i < than) {
				bitmap = defaults;
			}
			if (i == than) {
				bitmap = raiseYourhand;
			}
			if (i > than) {
				bitmap = toLetGo;
			}
			c.drawBitmap(bitmap, null, rectF, paint);
			x += offsetX;
		}
	}
}
