package com.example.lazada.view.trangchu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lazada.R;
import com.example.lazada.presenter.trangchu.PresenterDownload;

/**
 * Created by namtran on 13/02/2017.
 */

public class TrangChuActivity extends AppCompatActivity implements View.OnClickListener, ViewDownloadInterface {
    EditText etMaLoaiCha;
    Button btnLayDuLieu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu_layout);

        etMaLoaiCha = (EditText) findViewById(R.id.etMaLoaiCha);
        btnLayDuLieu = (Button) findViewById(R.id.btnLayDuLieu);
        btnLayDuLieu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String maLoaiCha = etMaLoaiCha.getText().toString();
        // GET method
        // String url = "http://192.168.1.227/weblazada/loaisanpham.php?maloaicha=" + maLoaiCha;
        // POST method
        String url = "http://192.168.1.227/weblazada/loaisanpham.php";
        PresenterDownload download = new PresenterDownload(this, url, maLoaiCha);
        download.downloadData();
        etMaLoaiCha.setText("");
    }

    @Override
    public void downloadSuccess(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void downloadFailed() {
        Toast.makeText(this, "Download failed!", Toast.LENGTH_SHORT).show();
    }
}
