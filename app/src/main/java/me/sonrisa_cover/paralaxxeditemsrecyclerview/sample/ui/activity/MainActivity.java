package me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.adapter.BaseParallaxFeedAdapter;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.adapter.ParallaxFeedAdapter;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.BaseListItem;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.model.ItemImage;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.ParallaxRecyclerView;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.view.ItemText;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.rv_main)
    ParallaxRecyclerView mFeedView;

    private BaseParallaxFeedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initFeed();

    }

    private void initFeed(){
        mAdapter = new ParallaxFeedAdapter(getFeedItems());
        mFeedView.setHasFixedSize(true);
        mFeedView.setAdapter(mAdapter);
        mFeedView.setHasFixedSize(true);
    }

    private List<BaseListItem> getFeedItems(){
        TypedArray imgs = getResources().obtainTypedArray(R.array.thumbs);
        String[] names = getResources().getStringArray(R.array.names);
        List<BaseListItem> items = new ArrayList<>();
        for(int i = 0;i<names.length;i++){
            items.add(new ItemImage(names[i], imgs.getResourceId(i,-1)));
            items.add(new ItemText(R.layout.layout_list_item_text,getResources().getString(R.string.placeholder_text_title)));
        }
        List<BaseListItem> list = new ArrayList<>();
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        list.addAll(items);
        return list;
    }

}
