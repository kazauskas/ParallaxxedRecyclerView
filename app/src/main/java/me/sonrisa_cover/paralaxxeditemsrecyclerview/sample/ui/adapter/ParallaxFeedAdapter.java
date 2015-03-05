package me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.adapter.BaseParallaxFeedAdapter;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.BaseListItem;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.ItemImage;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.view.model.ItemText;

/**
 * Created by vadim on 3/4/15.
 */
public class ParallaxFeedAdapter extends BaseParallaxFeedAdapter {

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
            super.onBindViewHolder(holder, position);
        }
    }
}
