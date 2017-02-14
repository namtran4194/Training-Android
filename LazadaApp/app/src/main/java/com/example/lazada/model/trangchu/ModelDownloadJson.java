package com.example.lazada.model.trangchu;

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

/**
 * Created by namtran on 14/02/2017.
 */

public class ModelDownloadJson extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        StringBuilder data = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            Uri.Builder uri = new Uri.Builder();
            uri.appendQueryParameter("maloaicha", params[1]);
            String query = uri.build().getEncodedQuery();

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            writer.write(query);
            writer.flush();
            writer.close();

            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            data = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            Log.d("Kiem tra", data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toString();
    }
}
