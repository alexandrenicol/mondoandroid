package fr.webnicol.mondoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    //private TextView balanceText;
    //private TextView spentTodayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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



        getBalance();
        getTransactions();

    }

    private void getBalance() {
        String accessToken = UserSingleton.getInstance().getAccessToken();
        String accountId = UserSingleton.getInstance().getAccountId();
        String balanceUrl = "/balance?account_id="+accountId;
        try {
            MondoAPI.get(balanceUrl , accessToken, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        Log.d("RESPONSE BALANCE", responseStr);
                        // Do what you want to do with the response.

                        try {
                            JSONObject balanceObj = new JSONObject(responseStr);
                            final Integer balance = balanceObj.getInt("balance");
                            final Integer spentToday = balanceObj.getInt("spend_today");

                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView balanceText = (TextView) findViewById(R.id.balanceText);
                                    TextView spentTodayText = (TextView) findViewById(R.id.spentTodayText);
                                    balanceText.setText(Integer.toString(balance));
                                    spentTodayText.setText(Integer.toString(spentToday));
                                }
                            });



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

    private void getTransactions() {
        String accessToken = UserSingleton.getInstance().getAccessToken();
        String accountId = UserSingleton.getInstance().getAccountId();
        String transactionsUrl = "/transactions?expand[]=merchant&account_id="+accountId;
        try {
            MondoAPI.get(transactionsUrl , accessToken, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseStr = response.body().string();
                        Log.d("RESPONSE TRANSACTIONS", responseStr);
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
