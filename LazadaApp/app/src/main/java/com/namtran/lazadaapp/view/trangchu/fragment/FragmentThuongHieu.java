package com.namtran.lazadaapp.view.trangchu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namtran.lazadaapp.R;

/**
 * Created by namtr on 25/04/2017.
 */

public class FragmentThuongHieu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thuong_hieu, container, false);
    }
}
