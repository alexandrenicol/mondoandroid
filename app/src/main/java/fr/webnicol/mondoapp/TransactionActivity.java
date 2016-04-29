package fr.webnicol.mondoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        String jsonData;

        if (extras != null) {
            jsonData = extras.getString("transaction");
            // and get whatever type user account id is
            Log.d("TRANSACTION", jsonData);
            try {
                JSONObject transaction = new JSONObject(jsonData);

                Integer amount = transaction.getInt("amount");
                String created = transaction.getString("created");
                String merchantName;
                String category;
                String emoji;
                String imageURL;
                String addressShortFormatted;

                if (!transaction.isNull("merchant")){
                    merchantName = transaction.getJSONObject("merchant").getString("name");
                    category = transaction.getJSONObject("merchant").getString("category");
                    emoji = transaction.getJSONObject("merchant").getString("emoji");
                    imageURL = transaction.getJSONObject("merchant").getString("logo");

                    JSONObject merchant = transaction.getJSONObject("merchant");

                    if (!merchant.isNull("address")){
                        addressShortFormatted = merchant.getJSONObject("address").getString("short_formatted");

                    }


                } else {
                    merchantName = transaction.getString("description");
                }


                TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
                toolbarTitle.setText(created);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
