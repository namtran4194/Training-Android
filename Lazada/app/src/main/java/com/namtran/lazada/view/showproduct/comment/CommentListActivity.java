package com.namtran.lazada.view.showproduct.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.CommentAdapter;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.model.objectclass.OnLoadMoreListener;
import com.namtran.lazada.model.objectclass.OnScrollRecyclerListener;
import com.namtran.lazada.presenter.showproduct.comment.CommentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 5/20/2017.
 */

public class CommentListActivity extends AppCompatActivity implements CommentView, OnLoadMoreListener {
    private RecyclerView mRecyclerComment;
    private ProgressBar mProgress;
    private int productCode;
    private CommentPresenter mCommentPresenter;
    private List<Comment> mCommentList;
    private OnScrollRecyclerListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.danhsachdanhgia_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        mRecyclerComment = (RecyclerView) findViewById(R.id.danhsachdanhgia_recycler);
        mProgress = (ProgressBar) findViewById(R.id.danhsachdanhgia_progress);

        mCommentList = new ArrayList<>();
        productCode = getIntent().getIntExtra("MASP", -1);
        mCommentPresenter = new CommentPresenter(this);

        showProgressBar();
        mCommentPresenter.layDanhSachDanhGiaTheoMaSP(Action.COMMENTARY_LIST, productCode, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ketQuaThemDanhGia(boolean result) {

    }

    @Override
    public void hienThiDanhSachDanhGia(List<Comment> commentaries) {
        hideProgressBar();
        if (commentaries != null && commentaries.size() > 0) {
            mCommentList.addAll(commentaries);
            CommentAdapter adapter = new CommentAdapter(this, mCommentList, 0);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerComment.setLayoutManager(layoutManager);
            mRecyclerComment.setAdapter(adapter);
            listener = new OnScrollRecyclerListener(this, layoutManager);
            mRecyclerComment.addOnScrollListener(listener);
        } else {
            Toast.makeText(this, "Không có đánh giá nào", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMore(int totalItems) {
        showProgressBar();
        mCommentPresenter.layDanhSachDanhGiaTheoMaSP(Action.COMMENTARY_LIST, productCode, totalItems);
        listener.setLoaded();
    }

    private void showProgressBar() {
        mProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }
}
