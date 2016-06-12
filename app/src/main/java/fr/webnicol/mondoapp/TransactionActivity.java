package fr.webnicol.mondoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.webnicol.mondoapp.imageLoader.ImageLoader;

public class TransactionActivity extends AppCompatActivity {

    MapView mapView;
    GoogleMap map;

    Double longitude;
    Double latitude;
    Double zoom;
    String merchantName;
    public ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        imageLoader = new ImageLoader(getApplicationContext());

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
                String category= "Not available";
                String emoji= "Not available";
                String imageURL = null;
                String addressShortFormatted = "Not available";

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

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                try {
                    Date createdDate = df1.parse(created);
                    created = df.format(createdDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
                toolbarTitle.setText(created);

                TextView textMerchant = (TextView) findViewById(R.id.textMerchant);
                TextView textAddress = (TextView) findViewById(R.id.textAddress);
                TextView textAmount = (TextView) findViewById(R.id.textAmount);
                TextView textCategory = (TextView) findViewById(R.id.textCategory);
                TextView textEmoji = (TextView) findViewById(R.id.textEmoji);
                ImageView imageLogo = (ImageView) findViewById(R.id.imageLogo);

                textMerchant.setText(merchantName);
                textAddress.setText(addressShortFormatted);
                textCategory.setText(category);
                textEmoji.setText(emoji);

                if (amount < 0 ){
                    textAmount.setText(Double.toString(amount/-100.0));
                } else {
                    textAmount.setText("+"+Double.toString(amount/100.0));
                }

                if (imageURL != null) {
                    imageLoader.DisplayImage(imageURL, imageLogo);
                }



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
