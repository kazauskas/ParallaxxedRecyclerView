package me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.model.FeedItem;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.adapter.FeedAdapter;
import me.sonrisa_cover.paralaxxeditemsrecyclerview.ui.view.ParallaxRecyclerView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.rv_main)
    ParallaxRecyclerView mFeedView;

    private FeedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initFeed();

    }

    private void initFeed(){
        mAdapter = new FeedAdapter(getFeedItems());
        mFeedView.setHasFixedSize(true);
        mFeedView.setAdapter(mAdapter);
        mFeedView.setHasFixedSize(true);
    }

    private List<FeedItem> getFeedItems(){
        TypedArray imgs = getResources().obtainTypedArray(R.array.thumbs);
        String[] names = getResources().getStringArray(R.array.names);
        List<FeedItem> items = new ArrayList<>();
        for(int i = 0;i<names.length;i++){
            items.add(new FeedItem(names[i], imgs.getResourceId(i,-1)));
        }
        List<FeedItem> list = new ArrayList<>();
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
