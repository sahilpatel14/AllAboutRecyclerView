package com.example.android.allaboutrecyclerview.data.models;

import java.util.Locale;

/**
 * Created by sahil-mac on 16/05/18.
 */

public class CreditCard {

    public String number;
    public String expiration;
    public int pin;
    public int cvv;

    public CreditCard(String number, String expiration, int pin, int cvv) {
        this.number = number;
        this.expiration = expiration;
        this.pin = pin;
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "{" +
                "cc no. -> %s, " +
                "expiration -> %s, " +
                "pin no. -> %d, " +
                "cvv -> %d" +
                "}", number, expiration, pin, cvv);
    }
}
