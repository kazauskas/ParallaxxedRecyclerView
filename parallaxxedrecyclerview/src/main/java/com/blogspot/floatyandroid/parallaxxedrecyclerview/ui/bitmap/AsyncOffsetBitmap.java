package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.Consts;

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
        if ((Consts.INCORRECT_SIZE == baseWidth) || (Consts.INCORRECT_SIZE == baseHeight)){
            throw new IllegalArgumentException("Base width and base height should be more than 0.");
        }
        this.ctx = ctx;
        mBitmapDecodeListener = bitmapDecodeListener;
        mBitmapResourceDecodingTask = new AsyncBitmapResourceDecoding();
        mBitmapResourceDecodingTask.execute(resId);
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
            onFullBitmapReady(result);
        }
    }

}
