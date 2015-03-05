package me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.BaseListItem;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.contract.IHasText;

/**
 * Created by vadim on 3/4/15.
 */
public class ItemText extends BaseListItem implements IHasText {

    private String mText;

    public ItemText(){}

    public ItemText(@NonNull Integer layoutResourceId, String text){
        super(layoutResourceId);
        setText(text);
    }

    @Override
    public String getText() {
        return mText;
    }

    @Override
    public void setText(String text) {
        mText = text;
    }

    public static String getClassType(){
        return new ItemText().getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new TextHolder(view);
    }

    public static class TextHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public TextHolder(View itemLayoutView) {
            super(itemLayoutView);
            text = (TextView) itemLayoutView.findViewById(R.id.tv_text);
        }
    }
}
