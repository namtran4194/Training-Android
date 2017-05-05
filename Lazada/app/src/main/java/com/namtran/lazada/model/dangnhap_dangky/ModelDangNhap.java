package com.namtran.lazada.model.dangnhap_dangky;

import android.support.v4.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

/**
 * Created by namtr on 02/05/2017.
 */

public class ModelDangNhap {
    private AccessToken accessToken;
    private AccessTokenTracker tracker;

    // lấy token đăng nhập fb
    public AccessToken getCurrentFBAccessToken() {
        tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        destroyTracker();
        return accessToken;
    }

    public GoogleApiClient getGoogleApiClient(FragmentActivity activity, GoogleApiClient.OnConnectionFailedListener listener) {
        GoogleApiClient googleApiClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, listener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return googleApiClient;
    }

    public GoogleSignInResult getGoogleSignInResult(GoogleApiClient googleApiClient) {
        OptionalPendingResult<GoogleSignInResult> result = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (result.isDone()) {
            return result.get();
        }
        return null;
    }

    // dừng theo dõi access token hiện tại
    private void destroyTracker() {
        tracker.stopTracking();
    }
}
