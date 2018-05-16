package com.example.android.allaboutrecyclerview.data.models;

import java.util.Locale;

/**
 * Created by sahil-mac on 16/05/18.
 */

public class Contact {

    public String name;
    public String surname;
    public String gender;
    public String region;
    public int age;
    public String title;
    public String phone;
    public String birthday;
    public String email;
    public String password;
    public String photo;

    public CreditCard creditCard;

    public Contact(String name, String surname, String gender, String region,
                   int age, String title, String phone,
                   String email, String password, String photo) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.region = region;
        this.age = age;
        this.title = title;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "{" +
                "name -> %s, " +
                "surname -> %s," +
                "gender -> %s," +
                "region -> %s," +
                "age -> %d," +
                "title -> %s," +
                "phone -> %s," +
                "email -> %s," +
                "password -> %s," +
                "credit card -> %s," +
                "photo -> %s}", name, surname, gender, region, age, title, phone, email, password, creditCard ,photo);
    }
}
