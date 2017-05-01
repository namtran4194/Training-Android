package com.namtran.lazadaapp.connection.internet;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by namtr on 26/04/2017.
 */

public class DownloadJSON extends AsyncTask<String, Void, String> {
    private static final String TAG = "DownloadJSON";
    private String mUrl;
    private List<HashMap<String, String>> attrs;
    private boolean method;

    public DownloadJSON(String url) {
        this.mUrl = url;
        method = true; // GET method
    }

    public DownloadJSON(String mUrl, List<HashMap<String, String>> attrs) {
        this.mUrl = mUrl;
        this.attrs = attrs;
        method = false; // POST method
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(this.mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (method) {
                return methodGET(connection);
            } else {
                return methodPOST(connection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String methodGET(HttpURLConnection connection) {
        StringBuilder data = new StringBuilder();
        try {
            connection.connect();
            InputStream stream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(streamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, data.toString()); // Logging
        return data.toString();
    }

    private String methodPOST(HttpURLConnection connection) {
        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();
            int count = attrs.size();
            String key = "", value = "";
            for (int i = 0; i < count; i++) {
                for (Map.Entry<String, String> values : attrs.get(i).entrySet()) {
                    key = values.getKey();
                    value = values.getValue();
                }

                builder.appendQueryParameter(key, value);
            }
            String query = builder.build().getEncodedQuery();

            OutputStream stream = connection.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
            BufferedWriter writer = new BufferedWriter(streamWriter);
            writer.write(query);
            writer.flush();

            return methodGET(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
