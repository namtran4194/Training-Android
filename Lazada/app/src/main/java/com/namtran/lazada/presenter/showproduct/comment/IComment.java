package com.namtran.lazada.presenter.showproduct.comment;

import com.namtran.lazada.model.objectclass.Action;
import com.namtran.lazada.model.objectclass.Comment;

/**
 * Created by namtr on 5/19/2017.
 */

interface IComment {
    void themDanhGia(Comment comment);

    void layDanhSachDanhGiaTheoMaSP(Action action, int maSP, int startIndex);
}
