package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by vadim on 3/2/15.
 */
public interface IAdapterParallaxable {
    public void parallaxItem(int offset, LinearLayoutManager layoutManager);
}
