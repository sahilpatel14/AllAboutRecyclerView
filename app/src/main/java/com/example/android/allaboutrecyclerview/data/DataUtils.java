package com.example.android.allaboutrecyclerview.data;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.allaboutrecyclerview.data.models.Contact;
import com.example.android.allaboutrecyclerview.data.models.CreditCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sahil-mac on 20/04/18.
 */

public final class DataUtils implements DataConstants {

    private static final String TAG = "DataUtils";

    private static List<Contact> contacts = new ArrayList<>();


    /**
     * Read json file containing contact data and put into a list of
     * Contact type objects.
     */
    public static List<Contact> getAllContacts(@NonNull Context context){

        try {
            InputStream inputStream = context.getAssets().open(FILENAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String contactString = new String(buffer, "UTF-8");

            JSONArray contactsJson = new JSONArray(contactString);

            for (int i = 0; i < contactsJson.length(); i++) {

                JSONObject contactJson = contactsJson.getJSONObject(i);

                Contact contact = new Contact(
                        contactJson.getString(JSON_KEY_NAME), contactJson.getString(JSON_KEY_SURNAME),
                        contactJson.getString(JSON_KEY_GENDER), contactJson.getString(JSON_KEY_REGION),
                        contactJson.getInt(JSON_KEY_AGE), contactJson.getString(JSON_KEY_TITLE),
                        contactJson.getString(JSON_KEY_PHONE), contactJson.getString(JSON_KEY_EMAIL),
                        contactJson.getString(JSON_KEY_PASSWORD), contactJson.getString(JSON_KEY_PHOTO));

                //  Setting the birthday. It's enclosed in a JsonObject
                contact.birthday = contactJson.getJSONObject(JSON_KEY_BIRTHDAY).getString(JSON_KEY_DMY);

                JSONObject creditJson = contactJson.getJSONObject(JSON_KEY_CREDIT_CARD);
                contact.creditCard = new CreditCard(
                        creditJson.getString(JSON_KEY_NUMBER), creditJson.getString(JSON_KEY_EXPIRATION),
                        creditJson.getInt(JSON_KEY_PIN), creditJson.getInt(JSON_KEY_SECURITY));

                contacts.add(contact);
            }

        }
        catch (Exception e){
            Log.e(TAG, "getAllShips: ",e);
        }

        return contacts;
    }


    /**
     * Returns a random colour and returns it as an Integer.
     */
    private static int getRandomColour() {
        Random random = new Random();
        return Color.argb(255,random.nextInt(), random.nextInt(), random.nextInt());
    }

}
