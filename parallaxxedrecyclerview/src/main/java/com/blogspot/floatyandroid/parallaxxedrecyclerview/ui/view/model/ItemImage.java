package com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blogspot.floatyandroid.parallaxxedrecyclerview.R;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model.contract.IHasImageResourceId;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.model.contract.IHasText;
import com.blogspot.floatyandroid.parallaxxedrecyclerview.ui.view.ImageViewOffset;

/**
 * Created by vadim on 3/2/15.
 */
public class ItemImage extends BaseListItem implements IHasImageResourceId, IHasText{

    private String mNameId = null;
    private Integer mDrawableId = null;
    private String mDrawableUrl = null;
    private Integer mPlaceholderColorResId = null;


    public ItemImage(){}

    public ItemImage(@Nullable String mName, @Nullable Integer mDrawableId) {
        super(R.layout.layout_list_item_image);
        setText(mName);
        setImageResId(mDrawableId);
    }

    public ItemImage(@Nullable String mName, @Nullable String drawableUrl, @NonNull Integer placeholderColorResId) {
        super(R.layout.layout_list_item_image);
        setText(mName);
        setDrawableUrl(drawableUrl);
        setPlaceholderColorResId(placeholderColorResId);
    }

    public String getDrawableUrl() {
        return mDrawableUrl;
    }

    public void setDrawableUrl(String mDrawableUrl) {
        this.mDrawableUrl = mDrawableUrl;
    }

    public Integer getPlaceholderColorResId() {
        return mPlaceholderColorResId;
    }

    public void setPlaceholderColorResId(Integer mPlaceholderColorResId) {
        this.mPlaceholderColorResId = mPlaceholderColorResId;
    }

    public static String getClassType(){
        return new ItemImage().getType();
    }

    @Override
    public Integer getImageResId() {
        return mDrawableId;
    }

    @Override
    public void setImageResId(Integer resId) {
        this.mDrawableId = resId;
    }

    @Override
    public String getText() {
        return mNameId;
    }

    @Override
    public void setText(String text) {
        this.mNameId = text;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new ParallaxImageHolder(view);
    }

    public static class ParallaxImageHolder extends RecyclerView.ViewHolder {

        public ImageViewOffset categoryThumb;
        public TextView categoryName;
        public ParallaxImageHolder(View itemLayoutView) {
            super(itemLayoutView);
            categoryThumb = (ImageViewOffset) itemLayoutView.findViewById(R.id.me_sonrisa_cover_paralaxxed_items_image_view_offset);
            categoryName = (TextView) itemLayoutView.findViewById(R.id.tv_name);
        }
    }

}
