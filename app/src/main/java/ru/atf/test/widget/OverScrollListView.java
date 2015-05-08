package ru.atf.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class OverScrollListView extends ListView {

    OnOverScrollListener onOverScrollListener;

    public OverScrollListView(Context context) {
        super(context);
    }

    public OverScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnOverScrollListener(OnOverScrollListener onOverScrollListener) {
        this.onOverScrollListener = onOverScrollListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (getFirstVisiblePosition() == 0) {
            if (onOverScrollListener != null) {
                onOverScrollListener.onOverScrollTop();
            }
        }
    }

    public interface OnOverScrollListener {
        void onOverScrollTop();
    }

}
