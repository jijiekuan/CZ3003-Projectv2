package com.example.jj.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JJ on 25/3/2017.
 */

public class ConnectDotsView extends View {

    private static final String TAG = "ConnectDotsViewClass";

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPointPaint;
    private Paint mPathPaint;
    private Paint mTextPaint;
    private static final int TOUCH_TOLERANCE_DP = 24;
    private static final int BACKGROUND = 0xFFFFFF;
    private List<Point> mPoints = new ArrayList<Point>();
    private int mLastPointIndex = 0;
    private int mTouchTolerance;
    private boolean isPathStarted = false;

    public ConnectDotsView(Context context) {
        super(context);
        mCanvas = new Canvas();
        mPath = new Path();
        initPointPaint();
        initPathPaint();
        initTextPaint();
    }

    public ConnectDotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCanvas = new Canvas();
        mPath = new Path();
        initPointPaint();
        initPathPaint();
        initTextPaint();
    }

    public ConnectDotsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCanvas = new Canvas();
        mPath = new Path();
        initPointPaint();
        initPathPaint();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        clear();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        canvas.drawColor(BACKGROUND);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPathPaint);

        int number = 1;

        // TODO remove if you do not want points to be drawn
        for (Point point : mPoints) {
            //Log.i(TAG, "Draw at x: " + point.x + " y : " + point.y);
            //canvas.drawPoint(point.x, point.y, mPointPaint);
            canvas.drawCircle(point.x, point.y, 32, mPointPaint);
            canvas.drawText(number+"", point.x, point.y+18, mTextPaint);
            number++;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // user touches the screen
                Log.i(TAG, "Action DOWN");
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // user drags finger across the screen
                Log.i(TAG, "Action MOVE");
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // user lifts finger off the screen
                Log.i(TAG, "Action UP");
                touch_up(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private void touch_start(float x, float y) {

        if (checkPoint(x, y, mLastPointIndex)) {
            mPath.reset();
            // draws a path if it starts from the point on the list pointed by mLastPointIndex
            isPathStarted = true;
        } else {
            // path is not drawn if it is not started from the next point on the list
            isPathStarted = false;
        }
    }

    private void touch_move(float x, float y) {

        if (isPathStarted) {
            mPath.reset();
            Point p = mPoints.get(mLastPointIndex);
            mPath.moveTo(p.x, p.y);
            if (checkPoint(x, y, mLastPointIndex + 1)) {
                p = mPoints.get(mLastPointIndex + 1);
                mPath.lineTo(p.x, p.y);
                mCanvas.drawPath(mPath, mPathPaint);
                mPath.reset();
                ++mLastPointIndex;
            } else {
                mPath.lineTo(x, y);
            }
        }
    }

    private void touch_up(float x, float y) {
        mPath.reset();
        if(checkPoint(x, y, mLastPointIndex + 1) && isPathStarted) {
            // move finished at valid point so draw whole line

            // start point
            Point p = mPoints.get(mLastPointIndex);
            mPath.moveTo(p.x, p.y);
            // end point
            p = mPoints.get(mLastPointIndex + 1);
            mPath.lineTo(p.x, p.y);
            mCanvas.drawPath(mPath, mPathPaint);
            mPath.reset();
            // increment point index
            mLastPointIndex++;
            isPathStarted = false;
        }

    }

    public void setPointPaint(Paint paint) {
        this.mPointPaint = paint;
    }

    public void setPathPaint(Paint paint) {
        this.mPathPaint = paint;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void clear() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(BACKGROUND);
        mCanvas.setBitmap(mBitmap);
        invalidate();
    }

    /**
     * Checks if user touch point with some tolerance
     */
    private boolean checkPoint(float x, float y, int pointIndex) {
        if (pointIndex == mPoints.size()) {
            // when the last dot has been reached
            Intent backToMain = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(backToMain);

            return false;
        }
        Point point = mPoints.get(pointIndex);
        if (x > (point.x - mTouchTolerance) && x < (point.x + mTouchTolerance)) {
            if (y > (point.y - mTouchTolerance) && y < (point.y + mTouchTolerance)) {
                return true;
            }
        }
        return false;
    }

    public List<Point> getPoints() {
        return mPoints;
    }

    public void setPoints(List<Point> points) {
        this.mPoints = points;
    }

    private void initTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(48);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);
    }

    private void initPointPaint() {
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointPaint.setStrokeJoin(Paint.Join.ROUND);
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointPaint.setStrokeWidth(32);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);
    }

    private void initPathPaint() {
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setDither(true);
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeWidth(32);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);
    }

    private int dp2px(int dp) {
        Resources r = getContext().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }
}
