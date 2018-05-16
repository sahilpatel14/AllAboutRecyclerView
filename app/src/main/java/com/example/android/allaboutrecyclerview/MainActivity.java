package com.example.android.allaboutrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.allaboutrecyclerview.data.DataUtils;
import com.example.android.allaboutrecyclerview.data.models.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Contact> contacts = DataUtils.getAllContacts(this);

        for (Contact contact : contacts) {
            Log.d(TAG, "onCreate: "+contact);
        }
    }

    //  You can use glide for image processing
    //  https://github.com/bumptech/glide
}
