package com.example.android.allaboutrecyclerview.data.models;

/**
 * Created by sahil-mac on 24/04/18.
 */

public class ShipListRow {

    public static final int ROW_TYPE_SHIP = 0;
    public static final int ROW_TYPE_GROUP = 1;

    public String groupName;
    public Ship ship;
    public int rowType;

    public ShipListRow(String groupName) {
        rowType = ROW_TYPE_GROUP;
        this.groupName = groupName;
    }

    public ShipListRow(Ship ship) {
        rowType = ROW_TYPE_SHIP;
        this.ship = ship;
    }


}
