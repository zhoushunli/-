package com.shunli.snow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by shunli on 2017/4/7.
 */

public class SnowView extends View {

    private static final int SNOW_SIZE = 15;
    private Paint mPaint;
    private ArrayList<Snow> arr;
    private Bitmap bg;

    public SnowView(Context context) {
        super(context);
        init();
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw | h != oldh) {
            prepareSnow(w, h);
            bg = getBitmap();
        }
    }

    private void prepareSnow(int screenWidth, int screenHeight) {
        arr = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 150; i++) {
            arr.add(new Snow(SNOW_SIZE, SNOW_SIZE, new PointF(random.nextInt(screenWidth),
                    random.nextInt(screenHeight)), Math.max(1, random.nextInt(SNOW_SIZE / 2))));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bg, 0, 0, null);
        for (Snow snow : arr) {
            snow.draw(canvas, mPaint);
        }
        getHandler().postDelayed(runnable, 10);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
        }
    };

    public void stop() {
        if (runnable != null)
            getHandler().removeCallbacks(runnable);
    }

    private Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg, options);
        int height = options.outHeight;
        int width = options.outWidth;
        float sampleSize = 1;
        if (height > getHeight()) {
            sampleSize = height / getHeight();
        }
        if (width > getWidth()) {
            sampleSize = width / getWidth();
        }
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = (int) sampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.bg, options);
    }
}
