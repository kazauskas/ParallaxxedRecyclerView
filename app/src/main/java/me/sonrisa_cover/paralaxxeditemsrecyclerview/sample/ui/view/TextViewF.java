package me.sonrisa_cover.paralaxxeditemsrecyclerview.sample.ui.view;

/**
 * Created by vadim on 3/5/15.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import me.sonrisa_cover.paralaxxeditemsrecyclerview.R;

public class TextViewF extends TextView {

    public TextViewF(Context context) {
        super(context);
    }

    public TextViewF(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFontAttribute(attrs);
    }

    public TextViewF(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFontAttribute(attrs);
    }

    private void initFontAttribute(AttributeSet attrs) {
        if (!this.isInEditMode()) {
            TypedArray a = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.WithFont);
            setTypeface(a.getString(R.styleable.WithFont_font_name));
        }
    }

    public void setTypeface(String path) {
        Typeface tf = Typeface.createFromAsset(this.getContext().getApplicationContext().getAssets(), path);
        this.setTypeface(tf);
    }

}