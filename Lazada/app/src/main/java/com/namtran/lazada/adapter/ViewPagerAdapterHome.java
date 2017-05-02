package com.namtran.lazada.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.namtran.lazada.view.trangchu.fragment.FragmentKhuyenMai;
import com.namtran.lazada.view.trangchu.fragment.FragmentDienTu;
import com.namtran.lazada.view.trangchu.fragment.FragmentLamDep;
import com.namtran.lazada.view.trangchu.fragment.FragmentMeVaBe;
import com.namtran.lazada.view.trangchu.fragment.FragmentNhaCuaVaDoiSong;
import com.namtran.lazada.view.trangchu.fragment.FragmentNoiBat;
import com.namtran.lazada.view.trangchu.fragment.FragmentTheThaoVaDuLich;
import com.namtran.lazada.view.trangchu.fragment.FragmentThoiTrang;
import com.namtran.lazada.view.trangchu.fragment.FragmentThuongHieu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namtr on 25/04/2017.
 */

public class ViewPagerAdapterHome extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private static final String[] TITLE_PAGE = new String[]{"Nổi Bật", "Khuyến Mãi", "Điện Tử", "Nhà Cửa & Đời Sống", "Mẹ & Bé",
                                                "Làm Đẹp", "Thời Trang", "Thể Thao & Du Lịch", "Thương Hiệu"};

    public ViewPagerAdapterHome(FragmentManager fm) {
        super(fm);
        fragments.add(new FragmentNoiBat());
        fragments.add(new FragmentKhuyenMai());
        fragments.add(new FragmentDienTu());
        fragments.add(new FragmentNhaCuaVaDoiSong());
        fragments.add(new FragmentMeVaBe());
        fragments.add(new FragmentLamDep());
        fragments.add(new FragmentThoiTrang());
        fragments.add(new FragmentTheThaoVaDuLich());
        fragments.add(new FragmentThuongHieu());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE_PAGE[position];
    }
}
