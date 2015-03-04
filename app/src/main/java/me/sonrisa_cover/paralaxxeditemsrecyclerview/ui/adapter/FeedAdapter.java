package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.adapter;

/**
 * Created by vadim on 3/2/15.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.model.FeedItem;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.IAdapterParallaxable;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.offset.ImageViewOffset;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.utils.TimeLogger;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> implements IAdapterParallaxable {

    private final static String TAG = "FeedAdapter";

    private List<FeedItem> mFeedData;

    private float fractialOffset = 0;

    public FeedAdapter(List<FeedItem> itemsData) {
        this.mFeedData = itemsData;
    }

    public void onViewRecycled (ViewHolder holder){
        holder.categoryThumb.cancel();
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item_feed_item, null);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FeedItem feed = mFeedData.get(position);
        viewHolder.categoryThumb.setImageResource(feed.getDrawableId());
        viewHolder.categoryName.setText(feed.getName());
    }

    @Override
    public void parallaxItem(int offset, LinearLayoutManager layoutManager) {

//        TimeLogger tl = new TimeLogger();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();
        int countVisible = lastVisible - firstVisible + 1;

        for(int i = 0;i<countVisible;i++){
            View visibleView = layoutManager.findViewByPosition(firstVisible + i);

            ImageViewOffset visibleImage = (ImageViewOffset) visibleView.findViewById(R.id.iv_background);
            visibleImage.offsetTo(offset, layoutManager.getHeight());
        }
//        tl.reset("PARALLAX_ITEM");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageViewOffset categoryThumb;
        public TextView categoryName;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            categoryThumb = (ImageViewOffset) itemLayoutView.findViewById(R.id.iv_background);
            categoryName = (TextView) itemLayoutView.findViewById(R.id.tv_name);
        }
    }

    @Override
    public int getItemCount() {
        return mFeedData.size();
    }
}