package com.example.android.allaboutrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.allaboutrecyclerview.data.DataUtils;
import com.example.android.allaboutrecyclerview.data.models.Ship;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Ship> ships = new ArrayList<>();
    private ShipsAdapter adapter = new ShipsAdapter();

    private RecyclerView rvPirateList;
    private ProgressBar progressBar;
    private RelativeLayout rlNoDataView;

    private static final String TAG = "MainActivity";

    private static final int NETWORK_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPirateList = findViewById(R.id.rv_ship_list);
        progressBar = findViewById(R.id.progress_bar);
        rlNoDataView = findViewById(R.id.rl_no_data);

        setupList();
    }

    private void setupList(){

        rvPirateList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvPirateList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvPirateList.setAdapter(adapter);

        //  Showing progress bar while the data is loading
        progressBar.setVisibility(View.VISIBLE);

        //  Getting data to be added to rvPirateList
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ships = DataUtils.getAllShips(MainActivity.this);

                // Disabling progressbar since data is available now.
                progressBar.setVisibility(View.GONE);

                //  If no ships found, show no data found view
                if (ships.isEmpty()) {
                    rlNoDataView.setVisibility(View.VISIBLE);
                    rvPirateList.setVisibility(View.GONE);
                } else {

                    //  We have received some ships data. Show
                    //  recycler view if its hidden and pass ship list to adapter.
                    rlNoDataView.setVisibility(View.GONE);
                    rvPirateList.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Total Ships : "+ships.size(),Toast.LENGTH_LONG).show();
                    adapter.setShips(ships);
                }
            }
        }, NETWORK_DELAY);

    }
}
