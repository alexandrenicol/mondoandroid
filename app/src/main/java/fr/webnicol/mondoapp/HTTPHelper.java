package fr.webnicol.mondoapp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by patex on 12/06/16.
 */
public class HTTPHelper {

    public static Call get(String url, Callback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String mUrl =  url;
        Request request = new Request.Builder()
                .url(mUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        String mUrl = url;
        Request request = new Request.Builder()
                .url(mUrl)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

}
