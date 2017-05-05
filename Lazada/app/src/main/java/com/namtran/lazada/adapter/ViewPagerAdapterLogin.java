package com.namtran.lazada.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.namtran.lazada.view.dangnhap_dangky.fragment.FragmentDangKy;
import com.namtran.lazada.view.dangnhap_dangky.fragment.FragmentDangNhap;

/**
 * Created by namtr on 01/05/2017.
 */

public class ViewPagerAdapterLogin extends FragmentPagerAdapter {
    private static final String[] TITLE = {"Đăng nhập", "Đăng ký"};

    public ViewPagerAdapterLogin(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentDangNhap();
            case 1:
                return new FragmentDangKy();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }
}
