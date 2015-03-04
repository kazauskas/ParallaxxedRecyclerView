package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.contract.IHasImageResourceId;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.contract.IHasText;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.offset.ImageViewOffset;

/**
 * Created by vadim on 3/2/15.
 */
public class ItemImage extends BaseListItem implements IHasImageResourceId, IHasText{

    private String mNameId;
    private int mDrawableId;

    public ItemImage(){}

    public ItemImage(String mName, Integer mDrawableId) {
        super(R.layout.layout_list_item_image);
        setText(mName);
        setImageResId(mDrawableId);
    }

    public static String getClassType(){
        return new ItemImage().getType();
    }

    @Override
    public int getImageResId() {
        return mDrawableId;
    }

    @Override
    public void setImageResId(int resId) {
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
