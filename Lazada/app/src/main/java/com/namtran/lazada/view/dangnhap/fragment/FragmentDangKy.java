package com.namtran.lazada.view.dangnhap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

/**
 * Created by namtr on 01/05/2017.
 */

public class FragmentDangKy extends Fragment implements Validator.ValidationListener, TextWatcher, View.OnFocusChangeListener {
    @Order(1)
    @NotEmpty(message = "Cần nhập mục này")
    private TextInputEditText mETFullName;

    @Order(2)
    @NotEmpty(sequence = 1, message = "Cần nhập mục này")
    @Email(sequence = 2, message = "Vui lòng nhập một email hợp lệ")
    private TextInputEditText mETEmail;

    @Order(3)
    @NotEmpty(sequence = 1, message = "Cần nhập mục này")
    @Length(sequence = 2, min = 6, max = 20, message = "Mật khẩu phải từ 6-20 ký tự")
    @Password(sequence = 3, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Mật khẩu phải chứa ít nhất một chữ số")
    private TextInputEditText mETPassword;

    @Order(4)
    @NotEmpty(sequence = 1, message = "Cần nhập mục này")
    @ConfirmPassword(sequence = 2, message = "Mật khẩu không khớp")
    private TextInputEditText mETRetype;

    private Validator mValidator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signUpView = inflater.inflate(R.layout.fragment_dangky, container, false);
        mETFullName = (TextInputEditText) signUpView.findViewById(R.id.signUp_et_name);
        mETEmail = (TextInputEditText) signUpView.findViewById(R.id.signUp_et_email);
        mETPassword = (TextInputEditText) signUpView.findViewById(R.id.signUp_et_password);
        mETRetype = (TextInputEditText) signUpView.findViewById(R.id.signUp_et_retype);

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

        mETFullName.setOnFocusChangeListener(this);
        mETEmail.setOnFocusChangeListener(this);
        mETPassword.setOnFocusChangeListener(this);
        mETRetype.setOnFocusChangeListener(this);

        mETFullName.addTextChangedListener(this);
        mETEmail.addTextChangedListener(this);
        mETPassword.addTextChangedListener(this);
        mETRetype.addTextChangedListener(this);

        return signUpView;
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getContext(), "Yay! we got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
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
    public void onFocusChange(View v, boolean hasFocus) {
//        mValidator.validate();
    }
}
