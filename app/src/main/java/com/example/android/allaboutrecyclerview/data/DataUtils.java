package com.example.android.allaboutrecyclerview.data;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.allaboutrecyclerview.data.models.Ship;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by sahil-mac on 20/04/18.
 */

public final class DataUtils implements DataConstants {

    private static final String TAG = "DataUtils";

    private static List<Ship> ships = new ArrayList<>();


    /**
     * Read json file containing ship's data and put into a list of
     * [Ship] type objects.
     */
    public static List<Ship> getAllShips(@NonNull Context context){

        ships.clear();

        try {
            InputStream inputStream = context.getAssets().open(FILENAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String shipsString = new String(buffer, "UTF-8");

            JSONArray shipsJson = new JSONArray(shipsString);

            for (int i = 0; i < shipsJson.length(); i++) {

                JSONObject shipJson = shipsJson.getJSONObject(i);

                ships.add(new Ship(
                        shipJson.getString(JSON_KEY_NAME),
                        shipJson.getString(JSON_KEY_CAPTAIN),
                        shipJson.getString(JSON_KEY_LENGTH),
                        shipJson.getString(JSON_KEY_WEAPONS),
                        shipJson.getString(JSON_KEY_TYPE),
                        shipJson.getString(JSON_KEY_FIRST_APPEARANCE),
                        getRandomColour()));

            }

        }
        catch (Exception e){
            Log.e(TAG, "getAllShips: ",e);
        }

        return ships;
    }

    /**
     * Returns a list of categories. In this context, it is
     * the name of movies in which that particular ship was first featured.
     */
    public static Set<String> getCategories(@NonNull Context context) {

        if (ships.isEmpty()) {
            getAllShips(context);
        }

        Set<String> categories = new HashSet<>();

        for (Ship ship : ships) {
            categories.add(ship.firstAppearance);
        }
        return categories;
    }


    /**
     * Returns a random colour and returns it as an Integer.
     */
    private static int getRandomColour() {
        Random random = new Random();
        return Color.argb(255,random.nextInt(), random.nextInt(), random.nextInt());
    }

}
