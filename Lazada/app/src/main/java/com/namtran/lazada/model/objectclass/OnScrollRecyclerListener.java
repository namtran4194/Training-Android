package com.namtran.lazada.model.objectclass;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by namtr on 5/15/2017.
 */

public class OnScrollRecyclerListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mIsLoading;

    public OnScrollRecyclerListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public OnScrollRecyclerListener(OnLoadMoreListener mOnLoadMoreListener, RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        this.mOnLoadMoreListener = mOnLoadMoreListener;
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

        int visibleThreshold = 10;
        // dy > 0: scroll down
        if (dy > 0 && !mIsLoading && totalItems <= (lastVisibleItem + visibleThreshold)) {
            mOnLoadMoreListener.onLoadMore(totalItems);
            mIsLoading = true;
        }
    }

    public void setmLayoutManager(RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public void setLoaded() {
        mIsLoading = false;
    }

}
