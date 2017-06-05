package com.namtran.lazada.view.signin_signup.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.namtran.lazada.R;
import com.namtran.lazada.connection.internet.Internet;
import com.namtran.lazada.model.objectclass.Staff;
import com.namtran.lazada.presenter.signup.SignUpPresenter;
import com.namtran.lazada.view.signin_signup.SignUpView;

import java.util.List;

/**
 * Created by namtr on 01/05/2017.
 */

public class SignUpFragment extends Fragment implements Validator.ValidationListener, TextWatcher, SignUpView, View.OnClickListener {
    @Order(1)
    @NotEmpty(message = "Cần nhập mục này", trim = true)
    private TextInputEditText mETFullName;

    @Order(2)
    @NotEmpty(sequence = 1, message = "Cần nhập mục này", trim = true)
    @Email(sequence = 2, message = "Vui lòng nhập một email hợp lệ")
    private TextInputEditText mETEmail;

    @Order(3)
    @NotEmpty(sequence = 1, message = "Cần nhập mục này", trim = true)
    @Length(sequence = 2, min = 6, max = 20, message = "Mật khẩu phải từ 6-20 ký tự")
    @Password(sequence = 3, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Mật khẩu phải chứa ít nhất một chữ số")
    private TextInputEditText mETPassword;

    @Order(4)
    @NotEmpty(sequence = 1, message = "Cần nhập mục này", trim = true)
    @ConfirmPassword(sequence = 2, message = "Mật khẩu không khớp")
    private TextInputEditText mETRetype;
    private SwitchCompat mSwitchCompat;

    private Validator mValidator;
    private SignUpPresenter signUpPresenter;
    private boolean allowRegistration;
    private Internet internet;
    public static final int SIGN_UP_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signUpView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        init(signUpView);

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
        mValidator.setViewValidatedAction(new Validator.ViewValidatedAction() {
            @Override
            public void onAllRulesPassed(View view) {
                View v = (View) view.getParentForAccessibility();
                if (v instanceof TextInputLayout) {
                    ((TextInputLayout) v).setErrorEnabled(false);
                    ((TextInputLayout) v).setError("");
                }
            }
        });

        signUpPresenter = new SignUpPresenter(this);
        internet = new Internet(getContext());

        return signUpView;
    }

    private void init(View v) {
        mETFullName = (TextInputEditText) v.findViewById(R.id.signUp_et_name);
        mETEmail = (TextInputEditText) v.findViewById(R.id.signUp_et_email);
        mETPassword = (TextInputEditText) v.findViewById(R.id.signUp_et_password);
        mETRetype = (TextInputEditText) v.findViewById(R.id.signUp_et_retype);
        Button btnSignUp = (Button) v.findViewById(R.id.signUp_btn_signup);
        Button btnWithFB = (Button) v.findViewById(R.id.signUp_btn_withFB);
        Button btnWithGG = (Button) v.findViewById(R.id.signUp_btn_withGG);
        mSwitchCompat = (SwitchCompat) v.findViewById(R.id.signUp_sc_offerViaEmail);

        mETFullName.addTextChangedListener(this);
        mETEmail.addTextChangedListener(this);
        mETPassword.addTextChangedListener(this);
        mETRetype.addTextChangedListener(this);

        btnSignUp.setOnClickListener(this);
        btnWithFB.setOnClickListener(this);
        btnWithGG.setOnClickListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        allowRegistration = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        allowRegistration = false;
        for (ValidationError error : errors) {
            View v = (View) error.getView().getParentForAccessibility();
            List<Rule> rules = error.getFailedRules();
            String message = rules.get(0).getMessage(getContext());

            if (v instanceof TextInputLayout) {
                ((TextInputLayout) v).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mValidator.validate();
    }

    @Override
    public void dangKyThanhCong() {
        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tenNV", mETFullName.getText().toString());
        editor.apply();

        getActivity().setResult(SIGN_UP_CODE);
        getActivity().finish();
    }

    @Override
    public void dangKyThatBai() {
        Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.signUp_btn_signup:
                mValidator.validate();
                if (allowRegistration) {
                    if (internet.isOnline())
                        dangKy();
                    else
                        Toast.makeText(getContext(), "Không có kết nối mạng, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signUp_btn_withFB:
                break;
            case R.id.signUp_btn_withGG:
        }
    }

    private void dangKy() {
        String name = mETFullName.getText().toString();
        String username = mETEmail.getText().toString();
        String password = mETPassword.getText().toString();
        boolean viaEmail = false;
        if (mSwitchCompat.isChecked())
            viaEmail = true;

        Staff staff = new Staff();
        staff.setStaffName(name);
        staff.setUsername(username);
        staff.setPassword(password);
        staff.setStaffTypeCode(3); // Khách hàng
        staff.setReceiveNewsViaEmail(String.valueOf(viaEmail));

        signUpPresenter.thucHienDangKy(staff);
    }
}
