package com.namtran.lazada.model.objectclass;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by namtr on 5/15/2017.
 */

public class LoadMoreRecycler extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreRecycler(RecyclerView.LayoutManager layoutManager, OnLoadMoreListener onLoadMoreListener) {
        this.mLayoutManager = layoutManager;
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItem = -1;

        int totalItems = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItem = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        int preloadedItem = 10;
        // dy > 0: scroll down
        if (dy > 0 && totalItems < (lastVisibleItem + preloadedItem)) {
            mOnLoadMoreListener.onLoadMore(totalItems);
        }
    }
}
