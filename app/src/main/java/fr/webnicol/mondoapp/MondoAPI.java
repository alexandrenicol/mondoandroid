package fr.webnicol.mondoapp;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alexandrenicol on 06/04/16.
 */
public class MondoAPI {
    public static final String BASE_URL = "https://api.getmondo.co.uk/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }



    public static void get(String token) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "Bearer "+token);
        client.get(getAbsoluteUrl("accounts"), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("+++SERVER+++", "onSuccess " + new String(bytes));
                try {
                    JSONArray response = new JSONArray(new String(bytes));
                    Log.d("+++SERVER+++", "onSuccess json to string" + response.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }

        });
    }

}
