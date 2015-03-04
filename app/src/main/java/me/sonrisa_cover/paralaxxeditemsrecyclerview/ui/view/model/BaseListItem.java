package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by vadim on 3/4/15.
 */
public abstract class BaseListItem {

    private Integer mLayoutResourceId;

    public BaseListItem(){}

    protected BaseListItem(@NonNull Integer layoutResourceId) {
        this.mLayoutResourceId = layoutResourceId;
    }

    public String getType(){
        return getClass().getName();
    }

    @NonNull
    public final Integer getLayoutResourceId(){
        return mLayoutResourceId;
    }

    @NonNull
    public abstract RecyclerView.ViewHolder getViewHolder(View view);

}
