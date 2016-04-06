package fr.webnicol.mondoapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

        MondoAPI.get(accessToken);
    }

}
