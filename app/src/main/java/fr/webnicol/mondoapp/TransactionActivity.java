package fr.webnicol.mondoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class TransactionActivity extends AppCompatActivity {

    MapView mapView;
    GoogleMap map;

    Double longitude;
    Double latitude;
    Double zoom;
    String merchantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                // Add a marker in Sydney and move the camera
                LatLng shop = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(shop).title(merchantName));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(shop, zoom.floatValue()));

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(TransactionActivity.this, MapsTransactionActivity.class);
                        intent.putExtra("longitude", longitude);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("zoom", zoom);
                        intent.putExtra("merchantName", merchantName);
                        startActivity(intent);
                    }
                });
            }
        });






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
                        latitude = merchant.getJSONObject("address").getDouble("latitude");
                        longitude = merchant.getJSONObject("address").getDouble("longitude");
                        zoom = merchant.getJSONObject("address").getDouble("zoom_level");


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


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
