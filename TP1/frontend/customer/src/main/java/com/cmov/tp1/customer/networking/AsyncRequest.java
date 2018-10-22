package com.cmov.tp1.customer.networking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AsyncRequest extends AsyncTask<String, Void, String> {
    private static final String TAG = "AsyncRequest";

    public interface OnTaskCompleted{
        void onTaskCompleted(JSONObject json);
    }

    private static final String HOST = "http://10.227.151.218";
    private OnTaskCompleted onTaskCompleted;

    AsyncRequest(OnTaskCompleted onTaskCompleted) {
        this.onTaskCompleted = onTaskCompleted;
    }

    private int writeBody(HttpURLConnection connection, String json) throws IOException {
        DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
        dStream.writeBytes(json);
        dStream.flush();
        dStream.close();

        return connection.getResponseCode();
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;

        // read to stringBuilder
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }

        reader.close();
        streamReader.close();

        return stringBuilder.toString();
    }

    protected String doInBackground(String... args) {
        String path = args[0];
        String method = args[1];
        String body = args.length > 2 ? args[2] : null;
        String result = null;

        if(path == null || method == null || // se campos obrigatorios forem null
                (body == null && (method.equals("POST") || method.equals("PUT")))) { // se for POST ou PUT sem body
            return null;
        }
        try {
            URL url = new URL(HOST + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();

            if(method.equals("POST") || method.equals("PUT")) {
                int responseCode = writeBody(connection, body);
                if(responseCode == HttpURLConnection.HTTP_OK)
                    result = getResponse(connection);
                else {
                    JSONObject res = new JSONObject();
                    res.put("message",connection.getResponseMessage());
                    res.put("code", responseCode);
                    result = res.toString();
                }
            } else {
                result = getResponse(connection);
            }


        } catch (IOException|JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(String result) {
        JSONObject json = null;
        if(result == null) {
            return;
        }
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onTaskCompleted.onTaskCompleted(json);
    }
}
