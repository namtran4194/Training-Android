package com.namtran.lazada.presenter.showproduct.comment;

import com.namtran.lazada.model.showproduct.comment.CommentModel;
import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.view.showproduct.comment.CommentView;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public class CommentPresenter implements IComment {
    private CommentView commentView;
    private CommentModel commentModel;

    public CommentPresenter(CommentView commentView) {
        this.commentView = commentView;
        commentModel = new CommentModel();
    }

    @Override
    public void themDanhGia(Comment comment) {
        Action action = Action.ADD_COMMENTARY;
        boolean result = commentModel.themDanhGia(action, comment);
        commentView.ketQuaThemDanhGia(result);
    }

    @Override
    public void layDanhSachDanhGiaTheoMaSP(Action action, int maSP, int startIndex) {
        List<Comment> commentList = commentModel.layDanhSachDanhGia(action, maSP, startIndex);
        commentView.hienThiDanhSachDanhGia(commentList);
    }
}
