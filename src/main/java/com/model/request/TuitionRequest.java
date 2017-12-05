package com.model.request;

/**
 * A student's tuition request
 *
 * @author Maysam Mokarian
 * @version 2.0
 * @since 13.11.2017
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

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
