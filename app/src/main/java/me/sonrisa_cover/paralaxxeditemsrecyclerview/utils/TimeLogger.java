package me.sonrisa_cover.paralaxxeditemsrecyclerview.utils;

import android.util.Log;

import java.util.Date;

/**
 * Created by vadim on 3/2/15.
 */
public class TimeLogger {

    private final long BASE_THRESHOLD = 1;

    private Date mOldDate;
    private Date mDate;

    public TimeLogger(){
        mDate = new Date();
        mOldDate = mDate;
    }

    public void reset(){
        mOldDate = mDate;
        mDate = new Date();
    }

    public void reset(String tag){
        mOldDate = mDate;
        mDate = new Date();
        long diff = mDate.getTime() - mOldDate.getTime();
        if (diff > BASE_THRESHOLD) Log.i(tag, "duration: " + diff + "ms");
    }

    public void reset(String tag, long threshold){
        mOldDate = mDate;
        mDate = new Date();
        long diff = mDate.getTime() - mOldDate.getTime();
        if (diff > threshold) Log.i(tag, "duration: " + diff + "ms");
    }

}
