package fr.webnicol.mondoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Bundle extras = getIntent().getExtras();
        String jsonData;

        if (extras != null) {
            jsonData = extras.getString("transaction");
            // and get whatever type user account id is
            Log.d("TRANSACTION", jsonData);
        }
    }
}
