package com.namtran.lazada.model.dangnhap;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

/**
 * Created by namtr on 02/05/2017.
 */

public class XuLyDangNhap {
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

    // dừng theo dõi access token hiện tại
    private void destroyTracker() {
        tracker.stopTracking();
    }
}
