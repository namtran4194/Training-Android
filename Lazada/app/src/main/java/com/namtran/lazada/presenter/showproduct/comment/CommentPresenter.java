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
    public void addComment(Comment comment) {
        Action action = Action.ADD_COMMENTARY;
        boolean result = commentModel.add(action, comment);
        commentView.addCommentResult(result);
    }

    @Override
    public void getCommentsByProductCode(Action action, int maSP, int startIndex) {
        List<Comment> commentList = commentModel.getAll(action, maSP, startIndex);
        commentView.showComments(commentList);
    }
}
