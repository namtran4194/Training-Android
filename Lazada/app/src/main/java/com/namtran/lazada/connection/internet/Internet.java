package com.namtran.lazada.connection.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by namtr on 03/05/2017.
 */

public class Internet {
    private Context context;

    public Internet(Context context) {
        this.context = context;
    }

    // kiểm tra mạng
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
