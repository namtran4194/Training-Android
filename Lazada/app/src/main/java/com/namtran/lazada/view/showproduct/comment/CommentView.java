package com.namtran.lazada.view.showproduct.comment;

import com.namtran.lazada.model.objectclass.Comment;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public interface CommentView {
    void ketQuaThemDanhGia(boolean result);

    void hienThiDanhSachDanhGia(List<Comment> commentaries);
}
