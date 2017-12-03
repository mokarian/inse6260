package com.model.request;

/**
 * Created by maysam.mokarian on 11/13/2017.
 */
public class TuitionRequest {
    String amount;
    String cardNum;
    String date;
    String cvv;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getDate() {
        return date;
    }

    public String getCvv() {
        return cvv;
    }
}
