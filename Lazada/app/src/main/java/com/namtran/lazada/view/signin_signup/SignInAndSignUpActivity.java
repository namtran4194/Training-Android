package com.namtran.lazada.view.signin_signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.namtran.lazada.R;
import com.namtran.lazada.adapter.PagerLoginAdapter;

/**
 * Created by namtr on 01/05/2017.
 */

public class SignInAndSignUpActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        PagerLoginAdapter adapter = new PagerLoginAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void init() {
        mTabLayout = (TabLayout) findViewById(R.id.login_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.login_viewPager);
        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
    }
}
