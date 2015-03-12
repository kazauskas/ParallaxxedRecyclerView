package me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.adapter.BaseParallaxFeedAdapter;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.ImageViewOffset;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model.BaseListItem;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model.ItemImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.view.ItemText;

/**
 * Created by vadim on 3/4/15.
 */
public class ParallaxFeedAdapter extends BaseParallaxFeedAdapter {

    private final static String TAG = "ParallaxFeedAdapter";

    DisplayImageOptions mOptions;

    public ParallaxFeedAdapter(List<BaseListItem> itemsData) {
        super(itemsData);
        mOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseListItem item = getItemByPosition(position);
        if (item.getType().equals(ItemText.getClassType())){
            ItemText text = (ItemText)item;
            ((ItemText.TextHolder)holder).text.setText(text.getText());
        }
        else if (item.getType().equals(ItemImage.getClassType())){
            ItemImage feed = (ItemImage)item;
            ItemImage.ParallaxImageHolder imageHolder = (ItemImage.ParallaxImageHolder)holder;
            if (null != feed.getImageResId()) imageHolder.categoryThumb.setImageResource(feed.getImageResId(), android.R.color.white);
            if (null != feed.getText()) imageHolder.categoryName.setText(feed.getText());
            ImageLoader.getInstance().displayImage(feed.getDrawableUrl(), imageHolder.categoryThumb, mOptions);

        }
    }

}
