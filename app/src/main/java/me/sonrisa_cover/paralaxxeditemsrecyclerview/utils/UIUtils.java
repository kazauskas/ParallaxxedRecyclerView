package me.sonrisa_cover.paralaxxeditemsrecyclerview.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by vadim on 3/10/15.
 */
public class UIUtils {

    public static Point getScreenDimensions(Context ctx){
        Display display = ((WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
