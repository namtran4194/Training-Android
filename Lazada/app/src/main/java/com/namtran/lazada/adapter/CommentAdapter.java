package com.namtran.lazada.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.Comment;

import java.util.List;

/**
 * Created by namtr on 5/20/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private List<Comment> mCommentList;
    private int mLimit;

    public CommentAdapter(Context context, List<Comment> commentList, int limit) {
        this.mContext = context;
        this.mCommentList = commentList;
        this.mLimit = limit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.custom_recycler_comment_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.mTVTitle.setText(comment.getTitle());
        holder.mTVContent.setText(comment.getContent());
        String commenter = "Được đăng bởi " + comment.getPhoneName() + " ngày " + comment.getDate();
        holder.mTVCommenter.setText(commenter);
        holder.mRating.setRating(comment.getNumOfStars());
    }

    @Override
    public int getItemCount() {
        if (mCommentList.size() > mLimit) return mLimit <= 0 ? mCommentList.size() : mLimit;
        return mCommentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTVTitle, mTVContent, mTVCommenter;
        RatingBar mRating;

        ViewHolder(View itemView) {
            super(itemView);

            mTVTitle = (TextView) itemView.findViewById(R.id.custom_recycler_danhgia_tieude);
            mTVContent = (TextView) itemView.findViewById(R.id.custom_recycler_danhgia_noidung);
            mTVCommenter = (TextView) itemView.findViewById(R.id.custom_recycler_danhgia_nguoidang);
            mRating = (RatingBar) itemView.findViewById(R.id.custom_recycler_danhgia_rating);
        }
    }
}
