package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.adapter;

/**
 * Created by vadim on 3/2/15.
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.BaseListItem;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.ItemImage;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.offset.ImageViewOffset;

public abstract class BaseParallaxFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IAdapterParallaxable {

    private final static String TAG = "FeedAdapter";

    private Map<String, Integer> mItemTypes;
    private Map<Integer, Integer> mLayoutIds;

    private List<BaseListItem> mFeedData;

    public BaseParallaxFeedAdapter(List<BaseListItem> itemsData) {
        this.mFeedData = itemsData;
        initTypesWithLayouts();
    }

    private void initTypesWithLayouts(){
        mItemTypes = new HashMap<>();
        mLayoutIds = new HashMap<>();
        int typesCounter = 0;
        for(BaseListItem item : mFeedData){
            if (null == mItemTypes.get(item.getType())){
                mItemTypes.put(item.getType(), typesCounter);
                mLayoutIds.put(typesCounter, item.getLayoutResourceId());
                typesCounter++;
            }
        }
    }

    protected RecyclerView.ViewHolder getHolderByType(View view, Integer type){
        for(BaseListItem item : mFeedData){
            if (type.equals(getTypeByListItem(item))){
                return item.getViewHolder(view);
            }
        }
        throw new IllegalArgumentException("Incorrect type: " + type + ". ListItem with this type doesn't present.");
    }

    protected @NonNull Integer getLayoutIdByType(Integer type){
        return mLayoutIds.get(type);
    }

    protected @NonNull Integer getTypeByListItem(@NonNull BaseListItem item){
        return mItemTypes.get(item.getType());
    }

    @Override
    public int getItemViewType(int position) {
        return getTypeByListItem(mFeedData.get(position));
    }

    public void onViewRecycled (RecyclerView.ViewHolder holder){
        if (mItemTypes.get(ItemImage.getClassType()).equals(holder.getItemViewType())){
            ((ItemImage.ParallaxImageHolder)holder).categoryThumb.cancel();
        }
    }

    protected BaseListItem getItemByPosition(int position){
        return mFeedData.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Integer resId = getLayoutIdByType(viewType);
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(resId, null);

        return getHolderByType(itemLayoutView, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemImage feed = ((ItemImage)mFeedData.get(position));
        ((ItemImage.ParallaxImageHolder)viewHolder).categoryThumb.setImageResource(feed.getImageResId());
        ((ItemImage.ParallaxImageHolder)viewHolder).categoryName.setText(feed.getText());
    }

    @Override
    public void parallaxItem(int offset, LinearLayoutManager layoutManager) {
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        int lastVisible = layoutManager.findLastVisibleItemPosition();
        int countVisible = lastVisible - firstVisible + 1;

        for(int i = 0;i<countVisible;i++){
            View visibleView = layoutManager.findViewByPosition(firstVisible + i);
            View visibleImage =  visibleView.findViewById(R.id.me_sonrisa_cover_paralaxxed_items_image_view_offset);
            if (null != visibleImage){
                ((ImageViewOffset)visibleImage).offsetTo(offset, layoutManager.getHeight());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFeedData.size();
    }
}