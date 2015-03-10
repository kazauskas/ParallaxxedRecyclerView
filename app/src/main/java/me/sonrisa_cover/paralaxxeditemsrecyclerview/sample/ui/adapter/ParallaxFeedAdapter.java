package me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.adapter.BaseParallaxFeedAdapter;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model.BaseListItem;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model.ItemImage;

import java.util.List;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.view.ItemText;

/**
 * Created by vadim on 3/4/15.
 */
public class ParallaxFeedAdapter extends BaseParallaxFeedAdapter {

    private final static String TAG = "ParallaxFeedAdapter";

    public ParallaxFeedAdapter(List<BaseListItem> itemsData) {
        super(itemsData);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseListItem item = getItemByPosition(position);
        if (item.getType().equals(ItemText.getClassType())){
            ItemText text = (ItemText)item;
            ((ItemText.TextHolder)holder).text.setText(text.getText());
        }
/* if holder looks like to ItemImageParallax we just call nested binding implementation*/
        else if (item.getType().equals(ItemImage.getClassType())){
            ItemImage feed = (ItemImage)item;
            ItemImage.ParallaxImageHolder imageHolder = (ItemImage.ParallaxImageHolder)holder;
            if (null != feed.getImageResId()) imageHolder.categoryThumb.setImageResource(feed.getImageResId(), android.R.color.white);
            if (null != feed.getText()) imageHolder.categoryName.setText(feed.getText());
        }
    }
}
