package fr.webnicol.mondoapp;

/**
 * Created by patex on 07/04/16.
 */
public class UserSingleton {
    private static UserSingleton mInstance = null;
    public static UserSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new UserSingleton();
        }
        return mInstance;
    }
    private UserSingleton(){
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }
    public void setLoaded(boolean loaded) {
        this.isLoaded = loaded;
    }

    private boolean isLoaded;
    private String accessToken;
    private String userId;
    private String clientId;
    private String accountId;
    private String username;

    public String getClientId() {
        return this.clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
