package fr.webnicol.mondoapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class AfterLoginActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);



        Uri uriCalled = getIntent().getData();
        String accessToken = uriCalled.getQueryParameter("access_token");
        String clientId = uriCalled.getQueryParameter("client_id");
        String userId = uriCalled.getQueryParameter("user_id");
        String refreshToken = uriCalled.getQueryParameter("refresh_token");
        UserSingleton.getInstance().setAccessToken(accessToken);
        UserSingleton.getInstance().setRefreshToken(refreshToken);
        UserSingleton.getInstance().setClientId(clientId);
        UserSingleton.getInstance().setUserId(userId);

        dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getData(1);
        if (cursor.getCount() < 1){
            Log.d("DB C", String.valueOf(dbHelper.insertCred(accessToken, userId, clientId, "", refreshToken, "")));
        } else {
            Log.d("DB U", String.valueOf(dbHelper.updateCred(1 ,accessToken, userId, clientId, "", refreshToken, "")));
        }


        //TextView accessTokenTV = (TextView) findViewById(R.id.accessTokenTV);
        //TextView clientIDTV = (TextView) findViewById(R.id.clientIDTV);
        //TextView userIDTV = (TextView) findViewById(R.id.userIDTV);

        //accessTokenTV.setText(accessToken);
        //clientIDTV.setText(clientId);
        //userIDTV.setText(userId);

        try {
            MondoAPI.get("accounts" , accessToken, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        Log.d("RESPONSE", responseStr);
                        // Do what you want to do with the response.
                        try {
                            JSONObject arr = new JSONObject(responseStr);
                            JSONArray accounts = arr.getJSONArray("accounts");
                            String accountId = accounts.getJSONObject(0).getString("id");
                            String username = accounts.getJSONObject(0).getString("description");

                            UserSingleton.getInstance().setAccountId(accountId);
                            UserSingleton.getInstance().setUsername(username);


                            dbHelper.updateCred(1, UserSingleton.getInstance().getAccessToken(),
                                    UserSingleton.getInstance().getUserId(), UserSingleton.getInstance().getClientId(),
                                    UserSingleton.getInstance().getAccountId(), UserSingleton.getInstance().getRefreshToken(),UserSingleton.getInstance().getUsername());

                            UserSingleton.getInstance().setLoaded(true);

                            Intent homeIntent = new Intent(AfterLoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Request not successful
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
