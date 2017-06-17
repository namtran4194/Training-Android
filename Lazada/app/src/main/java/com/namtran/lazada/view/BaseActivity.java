package com.namtran.lazada.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by namtr on 06/16/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected final void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
    }
}
