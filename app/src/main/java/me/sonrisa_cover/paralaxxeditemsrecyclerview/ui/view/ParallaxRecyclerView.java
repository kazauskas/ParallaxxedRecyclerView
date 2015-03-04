package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.IAdapterParallaxable;

/**
 * Created by vadim on 3/2/15.
 */
public class ParallaxRecyclerView extends RecyclerView {
    private final static String TAG = "ParallaxRecyclerView";

    private LinearLayoutManager mLayoutManager = null;

    private ParallaxRecyclerViewOnScrollListener mScrollListener = null;

    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        this.mLayoutManager = new LinearLayoutManager(this.getContext());
        this.setLayoutManager(mLayoutManager);

        this.mScrollListener = new ParallaxRecyclerViewOnScrollListener();
        this.setOnScrollListener(mScrollListener);
    }

    class ParallaxRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            ((IAdapterParallaxable)getAdapter()).parallaxItem(dy, mLayoutManager);
            super.onScrolled(recyclerView, dx, dy);
        }
    }

}
