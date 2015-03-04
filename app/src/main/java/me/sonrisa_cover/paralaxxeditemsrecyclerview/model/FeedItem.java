package me.sonrisa_cover.paralaxxeditemsrecyclerview.model;

/**
 * Created by vadim on 3/2/15.
 */
public class FeedItem {

    private String mNameId;
    private int mDrawableId;

    public FeedItem(String mNameId, int mDrawableId) {
        this.mNameId = mNameId;
        this.mDrawableId = mDrawableId;
    }

    public String getName() {
        return mNameId;
    }

    public void setNameId(String mNameId) {
        this.mNameId = mNameId;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public void setDrawableId(int mDrawableId) {
        this.mDrawableId = mDrawableId;
    }
}
