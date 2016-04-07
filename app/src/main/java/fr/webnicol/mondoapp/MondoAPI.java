package fr.webnicol.mondoapp;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;


/**
 * Created by alexandrenicol on 06/04/16.
 */
public class MondoAPI {
    public static final String BASE_URL = "https://api.getmondo.co.uk/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }



    public static Call get(String token, Callback callback) throws IOException {
        /*AsyncHttpClient client = new AsyncHttpClient();
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

        });*/

        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL+ "accounts";
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

}
