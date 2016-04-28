package fr.webnicol.mondoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    //private TextView balanceText;
    //private TextView spentTodayText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dbHelper = new DBHelper(this);

        if(!UserSingleton.getInstance().isLoaded()){
            Log.d("INSTANCE", "is not loaded");
            Log.d("INSTANCE", "is not loaded");
            Cursor res = dbHelper.getData(1);
            res.getCount();
            res.moveToFirst();
            Log.d("CURSOR", String.valueOf(res.getCount()));
            UserSingleton.getInstance().setAccessToken(res.getString(res.getColumnIndex("access_token")));
            UserSingleton.getInstance().setAccountId(res.getString(res.getColumnIndex("account_id")));
            UserSingleton.getInstance().setClientId(res.getString(res.getColumnIndex("client_id")));
            UserSingleton.getInstance().setUserId(res.getString(res.getColumnIndex("user_id")));
            UserSingleton.getInstance().setUsername(res.getString(res.getColumnIndex("username")));
            UserSingleton.getInstance().setRefreshToken(res.getString(res.getColumnIndex("refresh_token")));
            UserSingleton.getInstance().setLoaded(true);

            try {
                checkToken();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Log.d("INSTANCE", "is loaded");
            try {
                checkToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Log.d("INSTANCE TOKEN", UserSingleton.getInstance().getAccessToken());

        SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("REFRESH", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        loadData();
                    }
                }
        );

    }

    private void loadData () {
        getBalance();
        getTransactions();
    }

    private void refreshToken() throws IOException {
        String accessToken = UserSingleton.getInstance().getAccessToken();
        String refreshToken = UserSingleton.getInstance().getRefreshToken();
        String clientId = UserSingleton.getInstance().getClientId();
        JSONObject student1 = new JSONObject();
        try {
            student1.put("grant_type", "refresh_token");
            student1.put("client_id", clientId);
            student1.put("client_secret", "SsVaXOTB3PLHzgGOfbFpnhnumBsAVyJ5HcBXljzjhIiT5nT9Qj5iRDs0fl+HibHCcory/KxUN0oUc1R4QTRn");
            student1.put("refresh_token", refreshToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MondoAPI.post("oauth2/token", accessToken, student1.toString(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    Log.d("RESPONSE RTOKEN", responseStr);
                    JSONObject balanceObj = null;
                    try {
                        balanceObj = new JSONObject(responseStr);
                        String refreshToken = balanceObj.getString("refresh_token");
                        String accessToken = balanceObj.getString("access_token");
                        UserSingleton.getInstance().setAccessToken(accessToken);
                        UserSingleton.getInstance().setRefreshToken(refreshToken);
                        Log.d("RESPONSE RTOKEN at", accessToken);
                        Log.d("RESPONSE RTOKEN rt", refreshToken);
                        loadData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.d("RESPONSE RTOKEN ERROR",response.body().string());
                }
            }
        });
    }


    private void checkToken() throws IOException {
        String accessToken = UserSingleton.getInstance().getAccessToken();

        MondoAPI.get("ping/whoami" , accessToken, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    Log.d("RESPONSE WHOAMI", responseStr);
                    loadData();

                } else {
                    Log.d("RESPONSE WHOAMI","ertyui2");
                    refreshToken();
                }
            }
        });

        return ;
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
                                String imageURL;
                                String merchantName;
                                Log.d("MERCHANT", transaction.get("merchant").getClass().getName());
                                Integer amount = transaction.getInt("amount");
                                String created = transaction.getString("created");
                                if (!transaction.isNull("merchant")){
                                    merchantName = transaction.getJSONObject("merchant").getString("name");
                                    imageURL = transaction.getJSONObject("merchant").getString("logo");
                                    transactions.add(i, new Transaction(amount, merchantName, imageURL, created, transaction));
                                } else {
                                    merchantName = transaction.getString("description");
                                    transactions.add(i, new Transaction(amount, merchantName, created, transaction));
                                }


                            }
                            Collections.reverse(transactions);

                            HomeActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TransactionsAdapter transactionsAdapter = new TransactionsAdapter(HomeActivity.this, transactions);
                                    ListView listTransactions = (ListView) findViewById(R.id.listTransactions);
                                    listTransactions.setAdapter(transactionsAdapter);

                                    SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
                                    mySwipeRefreshLayout.setRefreshing(false);

                                    listTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(HomeActivity.this, TransactionActivity.class);
                                            Transaction entry = (Transaction) parent.getAdapter().getItem(position);
                                            intent.putExtra("transaction", entry.getData().toString());
                                            startActivity(intent);
                                        }
                                    });
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
