package com.example.android.allaboutrecyclerview;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.allaboutrecyclerview.data.DataUtils;
import com.example.android.allaboutrecyclerview.data.models.Ship;
import com.example.android.allaboutrecyclerview.data.models.ShipListRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private List<ShipListRow> rows = new ArrayList<>();
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

    /**
     * todo 4
     * Here we will inflate the created menu. This menu contains
     * searchView. We need to set the SearchableInfo for searchView and also
     * add a textChangedListener to listen to query changes.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchManager == null){
            throw new RuntimeException("How can it be null?");
        }

        ComponentName componentName = new ComponentName(this, MainActivity.class);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);

        searchView.setSearchableInfo(searchableInfo);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  Whenever the text for query is changes, we get a callback.
                handleSearch(newText);
                return false;
            }
        });

//      https://stackoverflow.com/questions/37919328/searchview-hint-not-showing
        searchView.setQueryHint(getString(R.string.hint_search_pirates));

        return true;
    }


    //  Here we handle the search query. We check if
    //  any ship name or captain name matches our query.
    //  if yes we create a separate list of those ships
    //  and update it in adapter.
    private void handleSearch(String searchQuery) {
        List<Ship> searchedShips = new ArrayList<>();
        for (ShipListRow row : rows) {
            if (row.rowType == ShipListRow.ROW_TYPE_SHIP && Utils.isShipAssociatedWith(searchQuery, row.ship)){
                searchedShips.add(row.ship);
            }
        }

        adapter.setRows(createRowsFromShips(searchedShips));
    }

    private void setupList(){

//        rvPirateList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvPirateList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (rows.get(position).rowType) {
                    case ShipListRow.ROW_TYPE_GROUP:
                        return 2;
                    case ShipListRow.ROW_TYPE_SHIP:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        rvPirateList.setLayoutManager(layoutManager);
        rvPirateList.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));

        rvPirateList.setAdapter(adapter);

        //  Showing progress bar while the data is loading
        progressBar.setVisibility(View.VISIBLE);

        //  Getting data to be added to rvPirateList
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                List<Ship> ships = DataUtils.getAllShips(MainActivity.this);
                rows.clear();
                rows.addAll(createRowsFromShips(ships));

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

                    adapter.setRows(rows);
                }
            }
        }, NETWORK_DELAY);

    }


    /**
     * Takes a list of ships as input and returns a list of rows for
     * shipList. It normalises group names from every ship and then it sorts the
     * whole list based on group name.
     */
    private List<ShipListRow> createRowsFromShips(List<Ship> ships) {

        Set<String> groupNames = new HashSet<>();
        List<ShipListRow> newRows = new ArrayList<>();

        //  getting all distinct group names and storing it in a Set
        for (Ship ship : ships) groupNames.add(ship.firstAppearance);

        //  Adds all the group names to rows list
        for (String name : groupNames) newRows.add(new ShipListRow(name));

        //  Adds all ships into rows list.
        for (Ship ship : ships) newRows.add(new ShipListRow(ship));

        //  Sorts rows list based on group name in descending order.
        Collections.sort(newRows, new Comparator<ShipListRow>() {
            @Override
            public int compare(ShipListRow s1, ShipListRow s2) {

                String c1 = s1.rowType == ShipListRow.ROW_TYPE_GROUP ?
                        s1.groupName :
                        s1.ship.firstAppearance;

                String c2 = s2.rowType == ShipListRow.ROW_TYPE_GROUP ?
                        s2.groupName :
                        s2.ship.firstAppearance;


                return String.CASE_INSENSITIVE_ORDER.compare(c2, c1);
            }
        });
        return newRows;
    }

}