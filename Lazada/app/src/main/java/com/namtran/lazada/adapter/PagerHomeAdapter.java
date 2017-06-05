package com.namtran.lazada.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.namtran.lazada.view.home.fragment.DiscountFragment;
import com.namtran.lazada.view.home.electronics.ElectronicsFragment;
import com.namtran.lazada.view.home.fragment.BeautyFragment;
import com.namtran.lazada.view.home.fragment.MomAndBabyFragment;
import com.namtran.lazada.view.home.fragment.HomeAndLivingFragment;
import com.namtran.lazada.view.home.fragment.HighlightFragment;
import com.namtran.lazada.view.home.fragment.SportsAndTravelFragment;
import com.namtran.lazada.view.home.fragment.FashionFragment;
import com.namtran.lazada.view.home.fragment.BrandFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 25/04/2017.
 */

public class PagerHomeAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private static final String[] PAGE_TITLES = new String[]{"Nổi Bật", "Khuyến Mãi", "Điện Tử", "Nhà Cửa & Đời Sống", "Mẹ & Bé",
            "Làm Đẹp", "Thời Trang", "Thể Thao & Du Lịch", "Thương Hiệu"};

    public PagerHomeAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new HighlightFragment());
        fragmentList.add(new DiscountFragment());
        fragmentList.add(new ElectronicsFragment());
        fragmentList.add(new HomeAndLivingFragment());
        fragmentList.add(new MomAndBabyFragment());
        fragmentList.add(new BeautyFragment());
        fragmentList.add(new FashionFragment());
        fragmentList.add(new SportsAndTravelFragment());
        fragmentList.add(new BrandFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }
}
