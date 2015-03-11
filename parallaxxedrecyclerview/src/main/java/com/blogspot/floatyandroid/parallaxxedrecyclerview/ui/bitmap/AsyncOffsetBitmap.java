package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.Consts;

import java.util.Date;

/**
 * Created by vadim on 3/3/15.
 */
public class AsyncOffsetBitmap implements IOffsetBitmap {

    private AsyncBitmapResourceDecoding mBitmapResourceDecodingTask;

    private Context ctx;

    private IBitmapResourceDecodeFinished mBitmapDecodeListener;

    private OffsetBitmap mBitmap = null;

    private int mBaseWidth = 0;
    private int mBaseHeight = 0;

    public AsyncOffsetBitmap(@NonNull Context ctx, @NonNull Integer resId, @NonNull Integer baseWidth, @NonNull Integer baseHeight, IBitmapResourceDecodeFinished bitmapDecodeListener){
        setBaseDimensions(baseWidth, baseHeight);
        this.ctx = ctx;
        mBitmapDecodeListener = bitmapDecodeListener;
        mBitmapResourceDecodingTask = new AsyncBitmapResourceDecoding();
        mBitmapResourceDecodingTask.execute(resId);
    }

    public AsyncOffsetBitmap(@NonNull Context ctx, @NonNull Bitmap bitmap, @NonNull Integer baseWidth, @NonNull Integer baseHeight, IBitmapResourceDecodeFinished bitmapDecodeListener){
        setBaseDimensions(baseWidth, baseHeight);
        this.ctx = ctx;
        mBitmapDecodeListener = bitmapDecodeListener;
        new AsyncBitmapScalingTask().execute(bitmap);
    }

    private void setBaseDimensions(int baseWidth, int baseHeight){
        if ((Consts.INCORRECT_SIZE == baseWidth) || (Consts.INCORRECT_SIZE == baseHeight)){
            throw new IllegalArgumentException("Base width and base height should be more than 0.");
        }
        this.mBaseWidth = baseWidth;
        this.mBaseHeight = baseHeight;
    }

    @Override
    public void addOffset(int offset, Integer scrollHeight){
        if (null != mBitmap){
            mBitmap.addOffset(offset, scrollHeight);
        }
    }

    @Override
    public void resetOffset(){
        if (null != mBitmap){
            mBitmap.resetOffset();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (null != mBitmap){
            mBitmap.draw(canvas);
        }
    }

    @Override
    public boolean isReady() {
        return null != mBitmap && mBitmap.isReady();
    }

    @Override
    public void cancel(){
        if (null != mBitmapResourceDecodingTask){
            mBitmapResourceDecodingTask.cancel(true);
        }
        this.resetOffset();
    }

    private void onFullBitmapReady(Bitmap bitmap){
        mBitmapResourceDecodingTask = null;
        mBitmap = new OffsetBitmap(bitmap, mBaseWidth, mBaseHeight);
        if (null != mBitmapDecodeListener) mBitmapDecodeListener.onBitmapResourceDecodeFinished(bitmap);
    }

    class AsyncBitmapResourceDecoding extends AsyncTask<Integer, Void, Bitmap> {

        private final static String TAG = "AsyncResourceDecoding";

        @Override
        protected Bitmap doInBackground(Integer... params) {
            return BitmapFactory.decodeResource(ctx.getResources(), params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result){
            new AsyncBitmapScalingTask().execute(result);
        }
    }

    class AsyncBitmapScalingTask extends AsyncTask<Bitmap, Void, Bitmap>{

        final float EPSILON = 0.01f;
        final float BORDER_PERCENT = 0.03f;//3 percents
        private final String TAG = "end dimensions v0.5";

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            float scaleCoefficient = 1;
            float startWidth = params[0].getWidth();
            float startHeight = params[0].getHeight();
            float containerWidth = mBaseWidth;
            float containerHeight = mBaseHeight;

            if (Math.abs(containerWidth - startWidth) > EPSILON){
                scaleCoefficient = containerWidth / startWidth;
            }

            if (startHeight*scaleCoefficient < Consts.PARALLAX_BITMAP_SCALED_FACTOR * containerHeight){
                scaleCoefficient = scaleCoefficient * ((Consts.PARALLAX_BITMAP_SCALED_FACTOR * containerHeight) / (startHeight * scaleCoefficient));
            }

            int scaledWidth = Math.round(startWidth * scaleCoefficient);
            int scaledHeight = Math.round(startHeight * scaleCoefficient);

            if (!((startWidth >= containerWidth) && ( Math.abs(scaledWidth - startWidth) <= startWidth * BORDER_PERCENT ))){
                params[0] = Bitmap.createScaledBitmap(params[0], scaledWidth, scaledHeight, true);
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(Bitmap result){
            onFullBitmapReady(result);
        }
    }

}
