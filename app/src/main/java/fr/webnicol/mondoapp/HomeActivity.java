package fr.webnicol.mondoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                            Integer balancePence = balanceObj.getInt("balance");
                            Integer todayPence = balanceObj.getInt("spend_today");
                            final double balance = balancePence.doubleValue()/100.0;
                            final double spentToday = todayPence.doubleValue()/(-100.0);

                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView balanceText = (TextView) findViewById(R.id.balanceText);
                                    TextView spentTodayText = (TextView) findViewById(R.id.spentTodayText);
                                    balanceText.setText(Double.toString(balance));
                                    spentTodayText.setText(Double.toString(spentToday));
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
                        try {
                            JSONObject transactionsRep = new JSONObject(responseStr);
                            JSONArray transactionsArr = transactionsRep.getJSONArray("transactions");

                            final ArrayList<Transaction> transactions = new ArrayList<Transaction>();


                            for (int i = 0; i < transactionsArr.length(); i++) {
                                JSONObject transaction = transactionsArr.getJSONObject(i);
                                String imageURL = transaction.getJSONObject("merchant").getString("logo");
                                if (transaction.getJson)
                                String merchantName = transaction.getJSONObject("merchant").getString("name");
                                Integer amount = transaction.getInt("amount");
                                transactions.add(i, new Transaction(amount, merchantName));

                            }

                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TransactionsAdapter transactionsAdapter = new TransactionsAdapter(HomeActivity.this, R.layout.listview_transactions, transactions.toArray(new Transaction[transactions.size()]));
                                    ListView listTransactions = (ListView) findViewById(R.id.listTransactions);
                                    listTransactions.setAdapter(transactionsAdapter);

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

}
