package com.namtran.lazada.view.showproduct.comment;

import com.namtran.lazada.model.objectclass.Comment;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public interface CommentView {

    // kết quả thêm đánh giá
    void addCommentResult(boolean result);

    // hiển thị danh sách đánh giá
    void showComments(List<Comment> commentList);
}
