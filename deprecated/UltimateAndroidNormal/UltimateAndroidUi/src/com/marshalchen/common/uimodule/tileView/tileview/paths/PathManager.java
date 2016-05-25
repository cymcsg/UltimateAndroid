package com.marshalchen.common.uimodule.tileView.tileview.paths;

import android.content.Context;
import android.graphics.*;
import com.marshalchen.common.uimodule.tileView.layouts.StaticLayout;
import com.marshalchen.common.uimodule.tileView.tileview.detail.DetailManager;

import java.util.ArrayList;
import java.util.List;

public class PathManager extends StaticLayout {

    private static final int DEFAULT_STROKE_COLOR = 0x883399FF;
    private static final int DEFAULT_STROKE_WIDTH = 8;

    private boolean shouldDraw = true;

    private Paint defaultPaint = new Paint();
    {
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setColor(DEFAULT_STROKE_COLOR);
        defaultPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        defaultPaint.setAntiAlias(true);
    }

    private DetailManager detailManager;

    private Path drawingPath = new Path();
    private Matrix matrix = new Matrix();

    private ArrayList<DrawablePath> paths = new ArrayList<DrawablePath>();

    public PathManager(Context context, DetailManager dm) {
        super(context);
        setWillNotDraw(false);
        detailManager = dm;
    }

    public Paint getPaint() {
        return defaultPaint;
    }

    public Path getPathFromPoints(List<Point> points) {
        Path path = new Path();
        Point start = points.get(0);
        path.moveTo((float) start.x, (float) start.y);
        int l = points.size();
        for (int i = 1; i < l; i++) {
            Point point = points.get(i);
            path.lineTo((float) point.x, (float) point.y);
        }
        return path;
    }

    public DrawablePath addPath(List<Point> points) {
        return addPath(points, defaultPaint);
    }

    public DrawablePath addPath(List<Point> points, Paint paint) {
        Path path = new Path();
        Point start = points.get(0);
        path.moveTo((float) start.x, (float) start.y);
        int l = points.size();
        for (int i = 1; i < l; i++) {
            Point point = points.get(i);
            path.lineTo((float) point.x, (float) point.y);
        }
        return addPath(path, paint);
    }

    public DrawablePath addPath(Path path, Paint paint) {
        DrawablePath drawablePath = new DrawablePath();
        drawablePath.path = path;
        drawablePath.paint = paint;
        return addPath(drawablePath);
    }

    public DrawablePath addPath(Path path) {
        return addPath(path, defaultPaint);
    }

    public DrawablePath addPath(DrawablePath drawablePath) {
        paths.add(drawablePath);
        invalidate();
        return drawablePath;
    }

    public void removePath(DrawablePath path) {
        paths.remove(path);
        invalidate();
    }

    public void clear() {
        paths.clear();
        invalidate();
    }

    public void setShouldDraw(boolean should) {
        shouldDraw = should;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (shouldDraw) {
            float scale = (float) detailManager.getScale();
            matrix.setScale(scale, scale);
            for (DrawablePath drawablePath : paths) {
                drawingPath.set(drawablePath.path);
                drawingPath.transform(matrix);
                // defer drawing to the path object
                drawablePath.draw(canvas, drawingPath);
            }
        }
        super.onDraw(canvas);
    }

}
