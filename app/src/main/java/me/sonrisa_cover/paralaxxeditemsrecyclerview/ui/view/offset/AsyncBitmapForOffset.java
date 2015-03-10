package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.offset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by vadim on 3/3/15.
 */
public class AsyncBitmapForOffset extends BitmapForOffset {

    private AsyncBitmapResourceDecoding mBitmapResourceDecodingTask;

    private Context ctx;

    private IBitmapResourceDecodeFinished mBitmapDecodeListener;

    public AsyncBitmapForOffset(@NonNull Context ctx, @NonNull Integer resId, @NonNull Integer baseWidth, @NonNull Integer baseHeight, IBitmapResourceDecodeFinished bitmapDecodeListener){
        if ((INCORRECT_SIZE == baseWidth) || (INCORRECT_SIZE == baseHeight)){
            throw new IllegalArgumentException("Base width and base height should be more than 0.");
        }
        this.ctx = ctx;
        mBitmapDecodeListener = bitmapDecodeListener;
        mBitmapResourceDecodingTask = new AsyncBitmapResourceDecoding();
        mBitmapResourceDecodingTask.execute(resId);
        initBaseWidthAndHeight(baseWidth, baseHeight);
    }

    public void cancel(){
        if (null != mBitmapResourceDecodingTask){
            mBitmapResourceDecodingTask.cancel(true);
        }
        this.resetOffset();
    }

    public void resetOffset(){
        this.offset = this.baseOffset;
    }

    public boolean isReady(){
        return getBitmap() != null;
    }

    private void onFullBitmapReady(Bitmap bitmap){
        mBitmapResourceDecodingTask = null;
        setBitmap(bitmap);
        initDimensions();
        mBitmapDecodeListener.onBitmapResourceDecodeFinished(bitmap);
    }

    class AsyncBitmapResourceDecoding extends AsyncTask<Integer, Void, Bitmap> {

        private final static String TAG = "AsyncResourceDecoding";

        @Override
        protected Bitmap doInBackground(Integer... params) {
            int resId = params[0];
            Bitmap bm =  BitmapFactory.decodeResource(ctx.getResources(), resId);
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            onFullBitmapReady(result);
        }
    }

}
