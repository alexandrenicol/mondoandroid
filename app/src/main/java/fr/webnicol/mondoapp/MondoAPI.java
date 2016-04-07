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

    public static Call get(String url, String token, Callback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String mUrl = BASE_URL+ url;
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url(mUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

}
