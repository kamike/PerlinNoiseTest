package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.noise.PerlinNoiseActivity;

import java.util.ArrayList;

/**
 * Created by wangtao on 2017/1/4.
 */

public class WaveDynamicDrawView extends View {
    private ArrayList<Float> pointList;

    public WaveDynamicDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        path = new Path();
        path.moveTo(0, initPointX);
    }

    private int viewWidth, viewHeight;
    private Paint paint;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        initPointX = h / 2;
    }

    private int initPointX = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        canvas.drawLine(0, viewHeight - initPointX, viewWidth, viewHeight - initPointX, paint);

        //=======================
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);
        path.lineTo(currentX, currentY + initPointX);

        canvas.drawPath(path, paint);
    }

    private Path path;
    private float x, y;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }


    //    public void updataValue(ArrayList<Float> pointList) {
//        this.pointList = pointList;
//        invalidate();
//    }
    private float currentX = 0;
    private float currentY;

    public void updataValue(float f) {
        if (currentX > PerlinNoiseActivity.maxLength) {
            path.offset(-20f, 0);
        } else {
            currentX += 20f;
        }
        this.currentY = f;
        invalidate();
    }

    public void resetView() {
        path.reset();
        path.moveTo(0, initPointX);
        invalidate();
    }
}
