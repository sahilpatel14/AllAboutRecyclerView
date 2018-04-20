package com.example.android.allaboutrecyclerview.data.models;

/**
 * Created by sahil-mac on 20/04/18.
 */

public class Ship {
    
    public final String name;
    public final String captain;
    public final String length;
    public final String weapons;
    public final String type;
    
    public final String firstAppearance;
    public final int captainsFavouriteColor;


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
