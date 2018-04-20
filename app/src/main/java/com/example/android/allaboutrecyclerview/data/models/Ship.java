package com.example.android.allaboutrecyclerview.data.models;

/**
 * Created by sahil-mac on 20/04/18.
 */

public class Ship {
    
    final String name;
    final String captain;
    final String length;
    final String weapons;
    final String type;
    
    public final String firstAppearance;
    final int captainsFavouriteColor;


    public Ship(String name, String captain,
                String length, String weapons,
                String type, String firstAppearance, int captainsFavouriteColor) {
        this.name = name;
        this.captain = captain;
        this.length = length;
        this.weapons = weapons;
        this.type = type;
        this.firstAppearance = firstAppearance;
        this.captainsFavouriteColor = captainsFavouriteColor;
    }
}
