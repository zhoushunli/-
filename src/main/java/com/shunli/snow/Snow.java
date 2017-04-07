package com.shunli.snow;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import java.util.Random;

/**
 * Created by shunli on 2017/4/7.
 */

public class Snow {
    private PointF mPosition;//位置
    private int width;//雪花的宽度
    private int height;//雪花的高度
    private float angle;//雪花的角度，用来处理偏移
    private float mVelocity=1.0f;
    private static final float SWING_SCALE=2;
    private int screenHeight;
    private int screenWidth;
    private final Random random;
    private static final int VELOCITY_SEED=5;

    public Snow(int width,int height,PointF position,float velocity){
        random = new Random();
        this.width= random.nextInt(width);
        this.height= random.nextInt(height);
        this.mPosition=position;
        this.mVelocity=velocity;
    }
    public void draw(Canvas canvas, Paint paint){
        screenHeight = canvas.getHeight();
        screenWidth = canvas.getWidth();
        canvas.drawCircle(mPosition.x,mPosition.y,Math.max(this.width, this.height)/2,paint);
        changePos();
    }
    public void changePos(){
        mPosition.x+=SWING_SCALE*Math.sin(angle);
        mPosition.y+=mVelocity;
        angle+=3;
        if (angle>=360){
            angle=0;
        }
        if (isOutOfBorder()){
            reset();
        }
    }
    private boolean isOutOfBorder(){
        return mPosition.x<0||mPosition.x>screenWidth||mPosition.y>screenHeight;
    }
    private void reset(){
        mPosition.x=random.nextInt(screenWidth);
        mPosition.y=random.nextInt(screenHeight/4);
        mVelocity=Math.max(random.nextInt(VELOCITY_SEED),1);
    }
}
