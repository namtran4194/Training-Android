package com.namtran.lazada.view.signin_signup.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.namtran.lazada.R;
import com.namtran.lazada.connection.internet.Internet;
import com.namtran.lazada.model.signin_signup.SignInModel;

import java.util.Collections;

/**
 * Created by namtr on 01/05/2017.
 */

public class SignInFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public static final int LOGIN_WITH_SOCIAL_NETWORK = 99;
    public static final int REQUEST_CODE_LOGIN_WITH_GG = 1;
    public static final int LOGIN_WITH_EMAIL = 1;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mDialog;
    private TextInputEditText mETEmail, mETPassword;
    private SignInModel signInModel;
    private Internet internet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        init(loginView);
        internet = new Internet(getContext());
        return loginView;
    }

    private void init(View v) {
        signInModel = new SignInModel();
        // fb login
        mCallbackManager = CallbackManager.Factory.create();
        // xử lý kết quả trả về khi đăng nhập bằng fb
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getActivity().setResult(LOGIN_WITH_SOCIAL_NETWORK);
                getActivity().finish();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "Đăng nhập thất bại, hãy đảm bảo bạn đã mở kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
        // gg login
        mGoogleApiClient = signInModel.getApi(getActivity(), this);

        mETEmail = (TextInputEditText) v.findViewById(R.id.login_etEmail);
        mETPassword = (TextInputEditText) v.findViewById(R.id.login_etPassword);

        Button btnLogin = (Button) v.findViewById(R.id.login_btnLogin);
        Button btnLoginFB = (Button) v.findViewById(R.id.login_withFB);
        Button btnLoginGG = (Button) v.findViewById(R.id.login_withGG);
        btnLogin.setOnClickListener(this);
        btnLoginFB.setOnClickListener(this);
        btnLoginGG.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // hiện đăng nhập với fb
        int id = v.getId();
        switch (id) {
            case R.id.login_btnLogin:
                String username = mETEmail.getText().toString();
                String password = mETPassword.getText().toString();
                if (internet.isOnline()) {
                    boolean isSuccessed = signInModel.checkingLogin(getContext(), username, password);
                    if (isSuccessed) {
                        getActivity().setResult(LOGIN_WITH_EMAIL);
                        getActivity().finish();
                    } else
                        Toast.makeText(getContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "Không có kết nối mạng, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_withFB:
                LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
                break;
            case R.id.login_withGG:
                showProgressDialog();
                Intent loginGG = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(loginGG, REQUEST_CODE_LOGIN_WITH_GG);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN_WITH_GG) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                getActivity().setResult(LOGIN_WITH_SOCIAL_NETWORK);
                getActivity().finish();
            } else
                Toast.makeText(getContext(), "Đăng nhập thất bại, hãy đảm bảo bạn đã mở kết nối mạng", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mDialog.hide();
        Log.e("Login with google", connectionResult.toString());
        Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
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
