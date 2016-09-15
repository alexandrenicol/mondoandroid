package fr.webnicol.mondoapp;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static Call post(String url, String token, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        String mUrl = BASE_URL+ url;
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url(mUrl)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static Call postForm(String url, String token, Map<String, String> payload , Callback callback) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();


        for (Map.Entry<String, String> entry : payload.entrySet())
        {
            formBuilder.add(entry.getKey(), entry.getValue());
        }

        RequestBody formBody = formBuilder.build();
        OkHttpClient client = new OkHttpClient();
        String mUrl = BASE_URL+ url;
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url(mUrl)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

}
