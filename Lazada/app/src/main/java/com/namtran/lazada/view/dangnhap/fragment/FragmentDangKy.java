package com.namtran.lazada.view.dangnhap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namtran.lazada.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by namtr on 01/05/2017.
 */

public class FragmentDangKy extends Fragment implements View.OnFocusChangeListener {
    private static final String PATTERN = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{6,20})";
    private TextInputEditText mETPassword, mETRetype;
    private TextInputLayout mInputLayout1, mInputLayout2;
    private Matcher matcher;
    private Pattern pattern;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signUpView = inflater.inflate(R.layout.fragment_dangky, container, false);
        mETPassword = (TextInputEditText) signUpView.findViewById(R.id.signUp_et_password);
        mInputLayout1 = (TextInputLayout) signUpView.findViewById(R.id.signUp_inputLayout1);
        mETRetype = (TextInputEditText) signUpView.findViewById(R.id.signUp_et_retype);
        mInputLayout2 = (TextInputLayout) signUpView.findViewById(R.id.signUp_inputLayout2);

        mETPassword.setOnFocusChangeListener(this);

        pattern = Pattern.compile(PATTERN);
        return signUpView;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (!hasFocus) {
            String password;
            switch (id) {
                case R.id.signUp_et_password:
                    password = mETPassword.getText().toString();
                    matcher = pattern.matcher(password);
                    if (!matcher.matches()) {
                        mInputLayout1.setErrorEnabled(true);
                        mInputLayout1.setError("Mật khẩu phải bao gồm ít nhất 6 kí tự và 1 chữ hoa");
                    } else {
                        mInputLayout1.setErrorEnabled(false);
                        mInputLayout1.setError("");
                    }
                case R.id.signUp_et_retype:
                    password = mETRetype.getText().toString();
                    matcher = pattern.matcher(password);
                    if (!matcher.matches()) {
                        mInputLayout1.setErrorEnabled(true);
                        mInputLayout1.setError("Mật khẩu phải bao gồm ít nhất 6 kí tự và 1 chữ hoa");
                    } else {
                        mInputLayout1.setErrorEnabled(false);
                        mInputLayout1.setError("");
                    }
            }
        }
    }
}
