package fr.webnicol.mondoapp;

/**
 * Created by patex on 08/04/16.
 */
public class Transaction {
    private String imageUrl;
    private Integer amount;
    private String merchantName;

    public Transaction(Integer amount, String merchantName) {
        this.amount = amount;
        this.merchantName = merchantName;
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