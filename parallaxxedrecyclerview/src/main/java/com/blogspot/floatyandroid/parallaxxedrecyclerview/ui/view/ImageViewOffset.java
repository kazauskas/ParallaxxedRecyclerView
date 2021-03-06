package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.R;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.Consts;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.UIUtils;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap.AsyncOffsetBitmap;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap.IBitmapResourceDecodeFinished;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap.IOffsetBitmap;


/**
 * Created by vadim on 3/3/15.
 */
public class ImageViewOffset extends ImageView implements IBitmapResourceDecodeFinished {
    private final static String TAG = "ImageViewOffset";

    private Integer mResourceId = null;

    private Bitmap mBitmapPrepared = null;

    private final int BLACK_MEDIUM_OPACITY = 0x6C000000;
    private final int BLACK_SMALL_OPACITY = 0x8C000000;

    private volatile LinearGradient mGradient = null;
    private volatile Paint mPaintBrush = null;
    private volatile RectF mRect = null;

    private int mStartColor = BLACK_SMALL_OPACITY;
    private int mEndColor = BLACK_MEDIUM_OPACITY;

    private IOffsetBitmap mSourceBitmap = null;

    public ImageViewOffset(Context context) {
        this(context, null);
    }

    public ImageViewOffset(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ImageViewOffset(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMinimumDimensions();
    }

    private void setMinimumDimensions(){
        this.setMinimumWidth(UIUtils.getScreenDimensions(getContext()).x);
        this.setMinimumHeight(Math.round(getResources().getDimension(R.dimen.feed_item_height)));
    }

    public void offsetTo(int offset, @NonNull Integer scrollHeight){
        if ((null != mSourceBitmap) && (0 != offset)){
            mSourceBitmap.addOffset(offset, scrollHeight);
            invalidate();
        }
    }

    public void cancel(){
        if (null != mSourceBitmap){
            mSourceBitmap.resetOffset();
            mSourceBitmap.cancel();
            mSourceBitmap = null;
        }
    }

    public void setImageBackground(int placeholderColorResId){
        this.setImageDrawable(new ColorDrawable(getResources().getColor(placeholderColorResId)));
    }

    public void setImageResource(int resId, int placeholderColorResId) {
        this.fadeOut();
        mBitmapPrepared = null;
        mSourceBitmap = null;
        mResourceId = resId;
        this.setImageDrawable(new ColorDrawable(getResources().getColor(placeholderColorResId)));
    }

    @Override
    public void setImageBitmap(@Nullable Bitmap bm){
        this.fadeOut();
        mSourceBitmap = null;
        mResourceId = null;
        mBitmapPrepared = bm;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        if ((Consts.INCORRECT_SIZE != getMeasuredWidth()) && (Consts.INCORRECT_SIZE != getMeasuredHeight())){
            if ((null == mSourceBitmap) && (null != mResourceId)){
                mSourceBitmap = new AsyncOffsetBitmap(this.getContext().getApplicationContext(), mResourceId, getMeasuredWidth(), getMeasuredHeight(), this);
            }
            if ((null == mSourceBitmap) && (null != mBitmapPrepared)){
                mSourceBitmap = new AsyncOffsetBitmap(this.getContext().getApplicationContext(), mBitmapPrepared, getMeasuredWidth(), getMeasuredHeight(), this);
            }
        }
        if (!isReady()){
            super.onDraw(canvas);
        }
        else{
            drawWithOffset(canvas);
        }
        drawOverlay(canvas);
    }

    protected void drawWithOffset(Canvas canvas){
        mSourceBitmap.draw(canvas);
    }

    protected void drawOverlay(Canvas canvas){
        if (null == mGradient){
            int[] colors = new int[] {mStartColor, mEndColor, mStartColor};
            mGradient = new LinearGradient(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), colors, null, android.graphics.Shader.TileMode.CLAMP);
        }

        if (null == mPaintBrush){
            mPaintBrush = new Paint();
            mPaintBrush.setDither(true);
            mPaintBrush.setShader(mGradient);
        }
        if (null == mRect)
            mRect = new RectF(0,0,canvas.getWidth(), canvas.getHeight());

        if (isReady())
            canvas.drawRect(mRect, mPaintBrush);
    }

    private boolean isReady(){
        return ((null != mSourceBitmap) && (mSourceBitmap.isReady()));
    }

    private void fadeOut(){
        this.setAlpha(0f);
    }

    private void fadeIn(){
        this.animate().alpha(1f).setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
    }

    @Override
    public void onBitmapResourceDecodeFinished(Bitmap bm) {
        fadeIn();
        invalidate();
    }
}