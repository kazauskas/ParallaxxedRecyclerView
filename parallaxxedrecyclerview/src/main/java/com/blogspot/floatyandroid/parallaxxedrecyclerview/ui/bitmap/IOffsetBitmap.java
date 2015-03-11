package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.bitmap;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

/**
 * Created by vadim on 3/11/15.
 */
public interface IOffsetBitmap {

    public void addOffset(int offset, @NonNull Integer scrollHeight);
    public void resetOffset();
    public void draw(Canvas canvas);
    public boolean isReady();

}
