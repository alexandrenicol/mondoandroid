package fr.webnicol.mondoapp;

import org.json.JSONObject;

/**
 * Created by patex on 08/04/16.
 */
public class Transaction {
    private String imageUrl = "";
    private Integer amount;
    private String merchantName;
    private String created;
    private JSONObject data;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Transaction(Integer amount, String merchantName, String created, JSONObject transaction) {
        this.amount = amount;
        this.merchantName = merchantName;
        this.created = created;
        this.data = transaction;
    }

    public Transaction(Integer amount, String merchantName, String imageUrl, String created, JSONObject transaction) {
        this.amount = amount;
        this.merchantName = merchantName;
        this.imageUrl = imageUrl;
        this.created = created;
        this.data = transaction;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
