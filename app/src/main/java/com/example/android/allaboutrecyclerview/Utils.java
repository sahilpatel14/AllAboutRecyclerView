package com.example.android.allaboutrecyclerview;

import android.util.Log;

import com.example.android.allaboutrecyclerview.data.models.Ship;

/**
 * Created by sahil-mac on 23/04/18.
 */

public final class Utils {


    //  Checks value for possible variations of query.
    //  bat would be checked for [bat, BAT, Bat]
    public static boolean checkWithAllPossibleCases(String query, String value) {

        String smallQuery = query.toLowerCase();
        String capsQuery = query.toUpperCase();
        String capitalizeQuery = "";

        if (query.length() > 0) {
            capitalizeQuery = query.substring(0,1).toUpperCase()+query.substring(1);
        }

        return value.contains(smallQuery) || value.contains(capsQuery) || value.contains(capitalizeQuery);
    }

    public static boolean isShipAssociatedWith(String query, Ship ship) {

        return checkWithAllPossibleCases(query, ship.name) ||
                checkWithAllPossibleCases(query, ship.captain);

    }
}
