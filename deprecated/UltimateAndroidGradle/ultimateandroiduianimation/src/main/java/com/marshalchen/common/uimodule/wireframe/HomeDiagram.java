/*
 * Copyright (c) 2014. Marshal Chen.
 */

package com.marshalchen.common.uimodule.wireframe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.marshalchen.common.uimodule.animation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 线框图
 * 
 * @author Administrator
 * 
 */
public class HomeDiagram extends View {

	private List<Integer> milliliter;
	private float tb;
	private float interval_left_right;
	private float interval_left;
	private Paint paint_date, paint_brokenLine, paint_dottedline,
			paint_brokenline_big, framPanint;

	private int time_index;
	private Bitmap bitmap_point;
	private Path path;
	private float dotted_text;

	public float getDotted_text() {
		return dotted_text;
	}

	public void setDotted_text(float dotted_text) {
		this.dotted_text = dotted_text;
	}

	private int fineLineColor = 0x5faaaaaa; // 灰色
	private int blueLineColor = 0xff00ffff; // 蓝色
	private int orangeLineColor = 0xffd56f2b; // 橙色

	public HomeDiagram(Context context, List<Integer> milliliter) {
		super(context);
		init(milliliter);
	}

	public void init(List<Integer> milliliter) {
		if (null == milliliter || milliliter.size() == 0)
			return;
		this.milliliter = delZero(milliliter);
		Resources res = getResources();
		tb = res.getDimension(R.dimen.historyscore_tb);
		interval_left_right = tb * 2.0f;
		interval_left = tb * 0.5f;

		paint_date = new Paint();
		paint_date.setStrokeWidth(tb * 0.1f);
		paint_date.setTextSize(tb * 1.2f);
		paint_date.setColor(fineLineColor);

		paint_brokenLine = new Paint();
		paint_brokenLine.setStrokeWidth(tb * 0.1f);
		paint_brokenLine.setColor(blueLineColor);
		paint_brokenLine.setAntiAlias(true);

		paint_dottedline = new Paint();
		paint_dottedline.setStyle(Paint.Style.STROKE);
		paint_dottedline.setColor(fineLineColor);

		paint_brokenline_big = new Paint();
		paint_brokenline_big.setStrokeWidth(tb * 0.4f);
		paint_brokenline_big.setColor(fineLineColor);
		paint_brokenline_big.setAntiAlias(true);

		framPanint = new Paint();
		framPanint.setAntiAlias(true);
		framPanint.setStrokeWidth(2f);

		path = new Path();
		bitmap_point = BitmapFactory.decodeResource(getResources(),
				R.drawable.wire_frame_icon_point_blue);
		setLayoutParams(new LayoutParams(
				(int) (this.milliliter.size() * interval_left_right),
				LayoutParams.MATCH_PARENT));
	}

	/**
	 * 移除左右为零的数据
	 * 
	 * @return
	 */
	public List<Integer> delZero(List<Integer> milliliter) {
		List<Integer> list = new ArrayList<Integer>();
		int sta = 0;
		int end = 0;
		for (int i = 0; i < milliliter.size(); i++) {
			if (milliliter.get(i) != 0) {
				sta = i;
				break;
			}
		}
		for (int i = milliliter.size() - 1; i >= 0; i--) {
			if (milliliter.get(i) != 0) {
				end = i;
				break;
			}
		}
		for (int i = 0; i < milliliter.size(); i++) {
			if (i >= sta && i <= end) {
				list.add(milliliter.get(i));
			}
		}
		time_index = sta;
		dotted_text = ((Collections.max(milliliter) - Collections
				.min(milliliter)) / 12.0f * 5.0f);
		return list;
	}

	protected void onDraw(Canvas c) {
		if (null == milliliter || milliliter.size() == 0)
			return;
		drawStraightLine(c);
		drawBrokenLine(c);
		drawDate(c);
	}

	/**
	 * 绘制竖线
	 * 
	 * @param c
	 */
	public void drawStraightLine(Canvas c) {
		paint_dottedline.setColor(fineLineColor);
		int count_line = 0;
		for (int i = 0; i < milliliter.size(); i++) {
			if (count_line == 0) {
				c.drawLine(interval_left_right * i, 0, interval_left_right * i,
						getHeight(), paint_date);
			}
			if (count_line == 2) {
				c.drawLine(interval_left_right * i, tb * 1.5f,
						interval_left_right * i, getHeight(), paint_date);
			}
			if (count_line == 1 || count_line == 3) {
				Path path = new Path();
				path.moveTo(interval_left_right * i, tb * 1.5f);
				path.lineTo(interval_left_right * i, getHeight());
				PathEffect effects = new DashPathEffect(new float[] { tb * 0.3f,
						tb * 0.3f, tb * 0.3f, tb * 0.3f }, tb * 0.1f);
				paint_dottedline.setPathEffect(effects);
				c.drawPath(path, paint_dottedline);
			}
			count_line++;
			if (count_line >= 4) {
				count_line = 0;
			}
		}
		c.drawLine(0, getHeight() - tb * 0.2f, getWidth(), getHeight() - tb
				* 0.2f, paint_brokenline_big);
	}

	/**
	 * 绘制折线
	 * 
	 * @param c
	 */
	public void drawBrokenLine(Canvas c) {
		int index = 0;
		float temp_x = 0;
		float temp_y = 0;
		float base = (getHeight() - tb * 3.0f)
				/ (Collections.max(milliliter) - Collections.min(milliliter));

		Shader mShader = new LinearGradient(0, 0, 0, getHeight(), new int[] {
				Color.argb(100, 0, 255, 255), Color.argb(45, 0, 255, 255),
				Color.argb(10, 0, 255, 255) }, null, Shader.TileMode.CLAMP);
		framPanint.setShader(mShader);

		for (int i = 0; i < milliliter.size() - 1; i++) {
			float x1 = interval_left_right * i;
			float y1 = getHeight() - tb * 1.5f - (base * milliliter.get(i));
			float x2 = interval_left_right * (i + 1);
			float y2 = getHeight() - tb * 1.5f - (base * milliliter.get(i + 1));

			if ((int) (base * milliliter.get(i + 1)) == 0 && index == 0) {
				index++;
				temp_x = x1;
				temp_y = y1;
			}
			if ((int) (base * milliliter.get(i + 1)) != 0 && index != 0) {
				index = 0;
				x1 = temp_x;
				y1 = temp_y;
			}
			if (index == 0) {
				c.drawLine(x1, y1, x2, y2, paint_brokenLine);
				path.lineTo(x1, y1);
				if (i != 0)
					c.drawBitmap(bitmap_point,
							x1 - bitmap_point.getWidth() / 2,
							y1 - bitmap_point.getHeight() / 2, null);
				if (i == milliliter.size() - 2) {
					path.lineTo(x2, y2);
					path.lineTo(x2, getHeight());
					path.lineTo(0, getHeight());
					path.close();
					c.drawPath(path, framPanint);
					c.drawBitmap(bitmap_point,
							x2 - bitmap_point.getWidth() / 2,
							y2 - bitmap_point.getHeight() / 2, null);
				}
			}
		}
		paint_dottedline.setColor(orangeLineColor);
		Path path = new Path();
		path.moveTo(0, getHeight() - tb * 6.5f);
		path.lineTo(getWidth(), getHeight() - tb * 6.5f);
		PathEffect effects = new DashPathEffect(new float[] { tb * 0.3f,
				tb * 0.3f, tb * 0.3f, tb * 0.3f }, tb * 0.1f);
		paint_dottedline.setPathEffect(effects);
		c.drawPath(path, paint_dottedline);

	}

	/**
	 * 绘制时间
	 * 
	 * @param c
	 */
	public void drawDate(Canvas c) {
		int hour = 0;
		String minute = "";
		float temp = time_index * 0.5f;
		if (temp % 1f == 0.5f) {
			hour = (int) temp;
			minute = ":30";
		} else {
			minute = ":00";
			hour = (int) temp;
		}
		for (int i = 0; i < milliliter.size(); i += 4) {
			paint_date.setStrokeWidth(tb * 2.8f);

			c.drawText(hour + minute, interval_left_right * i + interval_left,
					tb * 1.0f, paint_date);
			hour += 2;
		}

	}
}
