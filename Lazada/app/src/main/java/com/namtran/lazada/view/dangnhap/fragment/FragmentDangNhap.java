package com.namtran.lazada.view.dangnhap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.namtran.lazada.R;

import java.util.Collections;

/**
 * Created by namtr on 01/05/2017.
 */

public class FragmentDangNhap extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentDangNhap";
    public static final int RESULT_CODE_LOGIN = 1;
    private Button btnLoginFB;
    private CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_dang_nhap, container, false);

        init(loginView);
        return loginView;
    }

    private void init(View loginView) {
        callbackManager = CallbackManager.Factory.create();
        // xử lý kết quả trả về khi đăng nhập bằng fb
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getActivity().setResult(RESULT_CODE_LOGIN);
                getActivity().finish();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.toString());
            }
        });
        btnLoginFB = (Button) loginView.findViewById(R.id.login_withFB);
        btnLoginFB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // hiện đăng nhập với fb
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
