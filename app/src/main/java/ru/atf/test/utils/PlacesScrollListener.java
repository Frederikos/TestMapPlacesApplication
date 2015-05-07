package ru.atf.test.utils;

import android.widget.AbsListView;

public class PlacesScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 1;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean isLoading = true;
    private int startingPageIndex = 0;

    private OnLoadMoreListener onLoadMoreListener;

    public PlacesScrollListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex;
            previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                isLoading = true;
            }
        }

        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore(++currentPage, totalItemCount);
            }
            isLoading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int pageNumber, int totalItemsCount);
    }

}

