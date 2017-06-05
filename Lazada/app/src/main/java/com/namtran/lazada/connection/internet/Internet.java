package com.namtran.lazada.connection.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.namtran.lazada.view.home.HomeActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by namtr on 03/05/2017.
 */

public class Internet {
    private Context context;

    public Internet(Context context) {
        this.context = context;
    }

    // kiểm tra mạng
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public boolean isServerReachable() {
        AsyncTask task = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                if (isOnline()) {
                    try {
                        URL urlServer = new URL(HomeActivity.SERVER_NAME);
                        HttpURLConnection connection = (HttpURLConnection) urlServer.openConnection();
                        connection.setConnectTimeout(2000);
                        connection.connect();
                        if (connection.getResponseCode() == 200)
                            return true;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return false;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return false;
            }
        }.execute();
        try {
            return (boolean) task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
