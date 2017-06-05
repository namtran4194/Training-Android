package com.namtran.lazada.customview;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ExpandableListView;

/**
 * Created by namtr on 30/04/2017.
 */

// custom lại ExpandableListView
public class DynamicExpandableListView extends ExpandableListView {
    private Context context;
    private Point size = new Point();

    public DynamicExpandableListView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        display.getSize(size);
        int height = size.y;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
