package com.namtran.lazadaapp.view.manhinhchao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.namtran.lazadaapp.R;
import com.namtran.lazadaapp.view.trangchu.TrangChuActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    // handle error
                }finally {
                    Intent home = new Intent(SplashScreen.this, TrangChuActivity.class);
                    startActivity(home);
                    finish();
                }
            }
        });

        thread.start();
    }
}
