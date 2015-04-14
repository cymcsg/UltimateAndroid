/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.wireframe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.marshalchen.common.uimodule.animation.R;

import java.util.List;

/**
 * 柱状图
 * 
 * @author Administrator
 * 
 */
public class HomeColumnar extends View {

	private List<Score> score;
	private float tb;
	private float interval_left_right;
	private Paint paint_date, paint_rectf_gray, paint_rectf_blue;

	private int fineLineColor = 0x5faaaaaa; // 灰色
	private int blueLineColor = 0xff00ffff; // 蓝色

	public HomeColumnar(Context context, List<Score> score) {
		super(context);
		init(score);
	}

	public void init(List<Score> score) {
		if (null == score || score.size() == 0)
			return;
		this.score = score;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.historyscore_tb);
		interval_left_right = tb * 5.0f;
		
		paint_date = new Paint();
		paint_date.setStrokeWidth(tb * 0.1f);
		paint_date.setTextSize(tb * 1.2f);
		paint_date.setColor(fineLineColor);
		paint_date.setTextAlign(Align.CENTER);

		paint_rectf_gray = new Paint();
		paint_rectf_gray.setStrokeWidth(tb * 0.1f);
		paint_rectf_gray.setColor(fineLineColor);
		paint_rectf_gray.setStyle(Style.FILL);
		paint_rectf_gray.setAntiAlias(true);

		paint_rectf_blue = new Paint();
		paint_rectf_blue.setStrokeWidth(tb * 0.1f);
		paint_rectf_blue.setColor(blueLineColor);
		paint_rectf_blue.setStyle(Style.FILL);
		paint_rectf_blue.setAntiAlias(true);

		setLayoutParams(new LayoutParams(
				(int) (this.score.size() * interval_left_right),
				LayoutParams.MATCH_PARENT));
	}

	protected void onDraw(Canvas c) {
		if (null == score || score.size() == 0)
			return;
		drawDate(c);
		drawRectf(c);
	}

	/**
	 * 绘制矩形
	 * 
	 * @param c
	 */
	public void drawRectf(Canvas c) {
		for (int i = 0; i < score.size(); i++) {

			RectF f = new RectF();
			f.set(tb * 0.2f + interval_left_right * i,
					getHeight() - tb * 11.0f, tb * 3.2f + interval_left_right
							* i, getHeight() - tb * 2.0f);
			c.drawRoundRect(f, tb * 0.3f, tb * 0.3f, paint_rectf_gray);

			float base = score.get(i).score * (tb * 10.0f / 100);
			RectF f1 = new RectF();
			f1.set(tb * 0.2f + interval_left_right * i, getHeight()
					- (base + tb * 1.5f), tb * 3.2f + interval_left_right * i,
					getHeight() - tb * 1.5f);
			c.drawRoundRect(f1, tb * 0.3f, tb * 0.3f, paint_rectf_blue);
		}
	}

	/**
	 * 绘制日期
	 * 
	 * @param c
	 */
	public void drawDate(Canvas c) {
		for (int i = 0; i < score.size(); i++) {
			String date = score.get(i).date;
			String date_1 = date
					.substring(date.indexOf("-") + 1, date.length());
			c.drawText(date_1, tb * 1.7f + interval_left_right * i,
					getHeight(), paint_date);

		}
	}
}
