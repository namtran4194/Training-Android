package com.namtran.lazada.view.showproduct.comment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.namtran.lazada.R;
import com.namtran.lazada.model.objectclass.Comment;
import com.namtran.lazada.presenter.showproduct.comment.CommentPresenter;

import java.util.List;

/**
 * Created by namtr on 5/19/2017.
 */

public class AddCommentActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, View.OnClickListener, CommentView {
    private TextInputEditText mETTitle, mETContent;
    private Button mButtonOK;
    private int numOfStars;
    private int productCode;
    private CommentPresenter commentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_comment);

        init();
        productCode = getIntent().getIntExtra("MASP", -1);
        commentPresenter = new CommentPresenter(this);
    }

    private void init() {
        mETTitle = (TextInputEditText) findViewById(R.id.danhgia_et_tieude);
        mETContent = (TextInputEditText) findViewById(R.id.danhgia_et_noidung);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.danhgia_rb_sosao);
        ratingBar.setOnRatingBarChangeListener(this);
        mButtonOK = (Button) findViewById(R.id.danhgia_btn_dongy);
        mButtonOK.setOnClickListener(this);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        numOfStars = (int) rating;
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonOK) {
            doAddingComment();
        }
    }

    private void doAddingComment() {
        Comment comment = new Comment();
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String madg = manager.getDeviceId();
        String tenThietBi = Build.MODEL;

        String tieuDe = mETTitle.getText().toString().trim();
        String noiDung = mETContent.getText().toString().trim();

        View vTieuDe = (View) mETTitle.getParentForAccessibility();
        View vNoiDung = (View) mETContent.getParentForAccessibility();

        boolean isValid = true;
        if (tieuDe.length() > 0) {
            if (vTieuDe instanceof TextInputLayout)
                ((TextInputLayout) vTieuDe).setError("");
        } else {
            isValid = false;
            if (vTieuDe instanceof TextInputLayout)
                ((TextInputLayout) vTieuDe).setError("Bạn chưa nhập tiêu đề");
            else Toast.makeText(this, "Bạn chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
        }

        if (noiDung.length() > 0) {
            if (vNoiDung instanceof TextInputLayout)
                ((TextInputLayout) vNoiDung).setError("");
        } else {
            isValid = false;
            if (vNoiDung instanceof TextInputLayout)
                ((TextInputLayout) vNoiDung).setError("Bạn chưa nhập nội dung");
            else Toast.makeText(this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
        }

        if (isValid) {
            comment.setCommentaryCode(madg);
            comment.setProductCode(productCode);
            comment.setPhoneName(tenThietBi);
            comment.setTitle(tieuDe);
            comment.setContent(noiDung);
            comment.setNumOfStars(numOfStars);
            commentPresenter.addComment(comment);
        }
    }

    @Override
    public void addCommentResult(boolean result) {
        if (result) {
            Toast.makeText(this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else
            Toast.makeText(this, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComments(List<Comment> commentList) {

    }
}
