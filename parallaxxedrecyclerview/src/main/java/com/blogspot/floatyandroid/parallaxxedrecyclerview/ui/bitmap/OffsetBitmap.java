package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.Consts;

/**
 * Created by vadim on 3/3/15.
 */
public class OffsetBitmap {

    protected final static int INCORRECT_MIN_OFFSET = -1;
    protected int incorrectMaxOffset;

    protected Integer baseHeight;
    protected Integer baseWidth;

    protected Rect drawingRect;

    private Bitmap bitmap;
    protected int offset = 0;
    protected float offsetFraction = 0;
    protected int baseOffset = 0;

    public OffsetBitmap(@NonNull Bitmap bm, @NonNull Integer baseWidth, @NonNull Integer baseHeight){
        if ((Consts.INCORRECT_SIZE == baseWidth) || (Consts.INCORRECT_SIZE == baseHeight)){
            throw new IllegalArgumentException("Base width and base height should be more than 0.");
        }
        setBitmap(bm);
        initBaseWidthAndHeight(baseWidth, baseHeight);
        initDimensions();
    }

    public boolean isReady(){
        return getBitmap() != null;
    }

    public void resetOffset(){
        this.offset = this.baseOffset;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    protected void setBitmap(Bitmap bm){
        bitmap = bm;
    }

    protected void initBaseWidthAndHeight(@NonNull Integer baseWidth, @NonNull Integer baseHeight){
        this.baseHeight = baseHeight;
        this.baseWidth = baseWidth;
    }

    protected void initDimensions(){
        offset = (getBitmap().getHeight() - baseHeight) / 2;
        baseOffset = offset;
        incorrectMaxOffset = (getBitmap().getHeight() - baseHeight);
        drawingRect = new Rect(0,0,baseWidth, baseHeight);
    }

    protected int getOffset(){
        return offset;
    }

    public void addOffset(@NonNull Integer offset, @NonNull Integer scrollHeight){

        float parallaxCoef = ((float)incorrectMaxOffset / 2 ) / scrollHeight * (-1);

        float parallaxOffset = parallaxCoef * offset + offsetFraction;

        int decimalParallaxOffset = (int) parallaxOffset;
        offsetFraction = parallaxOffset - decimalParallaxOffset;

        if ((INCORRECT_MIN_OFFSET >= (getOffset() + decimalParallaxOffset)) || (incorrectMaxOffset < (getOffset() + decimalParallaxOffset) )){
            decimalParallaxOffset = 0;
        }

        this.offset+=decimalParallaxOffset;
    }

    private Rect getRectWithOffset(){
        return new Rect(0, getOffset(), baseWidth, baseHeight + getOffset());
    }

    private Rect getRectForDrawing(){
        return drawingRect;
    }

    public void draw(@NonNull Canvas canvas){
        canvas.drawBitmap(getBitmap(), getRectWithOffset(), getRectForDrawing(), null);
    }

}
