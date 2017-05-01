package com.namtran.lazadaapp.view.dangnhap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namtran.lazadaapp.R;

/**
 * Created by namtr on 01/05/2017.
 */

public class FragmentDangNhap extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        return loginView;
    }
}
