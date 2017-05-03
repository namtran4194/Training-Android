package com.namtran.lazada.view.dangnhap.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.namtran.lazada.R;
import com.namtran.lazada.model.dangnhap.XuLyDangNhap;

import java.util.Collections;

/**
 * Created by namtr on 01/05/2017.
 */

public class FragmentDangNhap extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "FragmentDangNhap";
    public static final int RESULT_CODE_LOGIN = 99;
    public static final int REQUEST_CODE_LOGIN = 1;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        init(loginView);
        return loginView;
    }

    private void init(View loginView) {
        // fb login
        mCallbackManager = CallbackManager.Factory.create();
        // xử lý kết quả trả về khi đăng nhập bằng fb
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
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
        // gg login
        mGoogleApiClient = new XuLyDangNhap().getGoogleApiClient(getActivity(), this);

        Button btnLoginFB = (Button) loginView.findViewById(R.id.login_withFB);
        Button btnLoginGG = (Button) loginView.findViewById(R.id.login_withGG);
        btnLoginFB.setOnClickListener(this);
        btnLoginGG.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // hiện đăng nhập với fb
        int id = v.getId();
        switch (id) {
            case R.id.login_withFB:
                LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
                break;
            case R.id.login_withGG:
                showProgressDialog();
                Intent loginGG = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(loginGG, REQUEST_CODE_LOGIN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                getActivity().setResult(RESULT_CODE_LOGIN);
                getActivity().finish();
            }
            mDialog.dismiss();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mDialog.hide();
        Log.d(TAG, connectionResult.toString());
    }

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getContext());
            mDialog.setTitle("Loading...");
        }
        mDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }
}
