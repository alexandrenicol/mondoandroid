package fr.webnicol.mondoapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class AfterLoginActivity extends AppCompatActivity {

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

        TextView accessTokenTV = (TextView) findViewById(R.id.accessTokenTV);
        TextView clientIDTV = (TextView) findViewById(R.id.clientIDTV);
        TextView userIDTV = (TextView) findViewById(R.id.userIDTV);

        accessTokenTV.setText(accessToken);
        clientIDTV.setText(clientId);
        userIDTV.setText(userId);

        try {
            MondoAPI.get(accessToken, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        Log.d("RESPONSE", responseStr);
                        // Do what you want to do with the response.
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
