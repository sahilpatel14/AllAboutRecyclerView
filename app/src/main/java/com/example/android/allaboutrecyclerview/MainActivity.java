package com.example.android.allaboutrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.allaboutrecyclerview.data.DataUtils;
import com.example.android.allaboutrecyclerview.data.models.Ship;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Ship> ships = new ArrayList<>();
    private ShipsAdapter adapter = new ShipsAdapter();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_ship_list);

        setupList();
    }

    private void setupList(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        //  Getting data to be added to recyclerView
        ships.clear();
        ships.addAll(DataUtils.getAllShips(this));

        Toast.makeText(this,"Total Ships : "+ships.size(),Toast.LENGTH_LONG).show();
        adapter.setShips(ships);
    }
}
