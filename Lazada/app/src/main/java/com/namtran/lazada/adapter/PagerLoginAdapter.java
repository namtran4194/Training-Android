package com.namtran.lazada.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.namtran.lazada.view.signin_signup.fragment.SignUpFragment;
import com.namtran.lazada.view.signin_signup.fragment.SignInFragment;

/**
 * Created by namtr on 01/05/2017.
 */

public class PagerLoginAdapter extends FragmentPagerAdapter {
    private static final String[] PAGE_TITLES = {"Đăng nhập", "Đăng ký"};

    public PagerLoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SignInFragment();
            case 1:
                return new SignUpFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }
}
