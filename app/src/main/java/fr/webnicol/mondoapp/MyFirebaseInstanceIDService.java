package fr.webnicol.mondoapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        try {
            sendRegistrationToServer(refreshedToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) throws IOException {
        // Add custom implementation, as needed.
        JSONObject data = new JSONObject();
        try {
            data.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HTTPHelper.post("https://node.webnicol.fr/mondo/firebase", data.toString(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("RESPONSE firebase","FAILURE");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    Log.d("RESPONSE firebase", responseStr);


                } else {
                    Log.d("RESPONSE firebase",response.body().string());
                }
            }
        });

    }

}

