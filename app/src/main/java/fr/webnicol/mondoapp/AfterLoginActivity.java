package fr.webnicol.mondoapp;

import android.content.Intent;
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
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Uri uriCalled = getIntent().getData();
        String accessToken = uriCalled.getQueryParameter("access_token");
        String clientId = uriCalled.getQueryParameter("client_id");
        String userId = uriCalled.getQueryParameter("user_id");
        UserSingleton.getInstance().setAccessToken(accessToken);
        UserSingleton.getInstance().setClientId(clientId);
        UserSingleton.getInstance().setUserId(userId);

        TextView accessTokenTV = (TextView) findViewById(R.id.accessTokenTV);
        TextView clientIDTV = (TextView) findViewById(R.id.clientIDTV);
        TextView userIDTV = (TextView) findViewById(R.id.userIDTV);

        accessTokenTV.setText(accessToken);
        clientIDTV.setText(clientId);
        userIDTV.setText(userId);

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

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent homeIntent = new Intent(AfterLoginActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }

}
