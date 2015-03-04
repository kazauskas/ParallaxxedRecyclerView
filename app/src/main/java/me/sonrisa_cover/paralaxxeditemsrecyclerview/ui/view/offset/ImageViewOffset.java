package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.offset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by vadim on 3/3/15.
 */
public class ImageViewOffset extends ImageView {
    private final static String TAG = "ImageViewOffset";

    private Integer mResourceId = null;

    private final int BLACK_MEDIUM_OPACITY = 0x6C000000;
    private final int BLACK_SMALL_OPACITY = 0x8C000000;

    private volatile LinearGradient mGradient = null;
    private volatile Paint mPaintBrush = null;
    private volatile RectF mRect = null;

    private int mStartColor = BLACK_SMALL_OPACITY;
    private int mEndColor = BLACK_MEDIUM_OPACITY;

    private AsyncBitmapForOffset mSourceBitmap = null;

    public ImageViewOffset(Context context) {
        this(context, null);
    }

    public ImageViewOffset(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ImageViewOffset(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mResourceId = resId;
    }

    @Override
    protected void onDraw(Canvas canvas){
        checkIsSourceBitmapInitialized();
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

        if (null != getDrawable())
            canvas.drawRect(mRect, mPaintBrush);
    }

    private void checkIsSourceBitmapInitialized(){
        if ((null == mSourceBitmap) && (null != mResourceId)){
            mSourceBitmap = new AsyncBitmapForOffset(this.getContext().getApplicationContext(), mResourceId, getMeasuredWidth(), getMeasuredHeight());
        }
    }

    private boolean isReady(){
        return ((null != mSourceBitmap) && (mSourceBitmap.isReady()));
    }

}