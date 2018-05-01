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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.allaboutrecyclerview.common.GridSpacingItemDecoration;
import com.example.android.allaboutrecyclerview.data.DataUtils;
import com.example.android.allaboutrecyclerview.data.models.Ship;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShipsAdapter.ShipClickedCallback{

    private boolean inGridMode = false;

    private List<Ship> ships = new ArrayList<>();
    private ShipsAdapter adapter;

    private RecyclerView.ItemDecoration listDividerItemDecoration;
    private RecyclerView.ItemDecoration gridSpacingItemDecoration;

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

        listDividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 25, true);

        setupList();
        getData();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_switch_layout:
                inGridMode = !inGridMode;
                setupList();
                item.setIcon(inGridMode?
                        R.drawable.ic_list_white_24dp:
                        R.drawable.ic_dashboard_white_24dp);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onShipClicked(Ship ship) {

        ShipDetailsDialog dialog = ShipDetailsDialog.newInstance(ship);
        dialog.show(getSupportFragmentManager(),"My dialog");

    }

    //  Here we handle the search query. We check if
    //  any ship name or captain name matches our query.
    //  if yes we create a separate list of those ships
    //  and update it in adapter.
    private void handleSearch(String searchQuery) {
        List<Ship> searchedShips = new ArrayList<>();
        for (Ship ship : ships) {
            if (Utils.isShipAssociatedWith(searchQuery, ship)){
                searchedShips.add(ship);
            }
        }
        adapter.setShips(searchedShips);
    }

    private void setupList(){

        adapter = new ShipsAdapter(inGridMode, this);

        rvPirateList.removeItemDecoration(listDividerItemDecoration);
        rvPirateList.removeItemDecoration(gridSpacingItemDecoration);

        if (inGridMode) {
            rvPirateList.setLayoutManager(new GridLayoutManager(this, 2));
            rvPirateList.addItemDecoration(gridSpacingItemDecoration);
        } else {
            rvPirateList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvPirateList.addItemDecoration(listDividerItemDecoration);
        }

        rvPirateList.setAdapter(adapter);
        adapter.setShips(ships);
        runLayoutAnimation();
    }

    private void getData() {

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

    private void runLayoutAnimation() {

        LayoutAnimationController animationController =
                AnimationUtils.loadLayoutAnimation(this,
                        inGridMode ?
                                R.anim.layout_animation_move_up :
                                R.anim.layout_animation_fall_down);

        rvPirateList.setLayoutAnimation(animationController);
    }
}
