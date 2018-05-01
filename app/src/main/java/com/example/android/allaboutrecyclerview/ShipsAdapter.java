package com.example.android.allaboutrecyclerview;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.allaboutrecyclerview.data.models.Ship;
import com.example.android.allaboutrecyclerview.data.models.ShipListRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sahil-mac on 20/04/18.
 */

public class ShipsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ShipListRow> rows = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = null;
        switch (viewType) {
            case ShipListRow.ROW_TYPE_GROUP:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_group_name, parent, false);
                return new GroupViewHolder(itemView);

            case ShipListRow.ROW_TYPE_SHIP:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_ship, parent, false);
                return new ShipViewHolder(itemView);

            default: return new GroupViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ShipListRow row = rows.get(position);

        if (row.rowType == ShipListRow.ROW_TYPE_GROUP){
            ((GroupViewHolder)holder).onBind(row.groupName);
        } else {
            ((ShipViewHolder)holder).onBind(row.ship);
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    @Override
    public int getItemViewType(int position) {
        return rows.get(position).rowType;
    }

    public void setRows(List<ShipListRow> newRows) {
        this.rows.clear();
        this.rows.addAll(newRows);
        notifyDataSetChanged();
    }

    static class ShipViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivShipImage;
        private TextView tvShipName;
        private TextView tvCaptainName;

        ShipViewHolder(View itemView) {
            super(itemView);

            ivShipImage = itemView.findViewById(R.id.iv_ship_image);
            tvShipName = itemView.findViewById(R.id.tv_ship_name);
            tvCaptainName = itemView.findViewById(R.id.tv_captain_name);
        }

        void onBind(Ship ship) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                GradientDrawable drawable = (GradientDrawable) ivShipImage.getBackground();
                drawable.setColor(ColorStateList.valueOf(ship.captainsFavouriteColor));
            }
            tvCaptainName.setText(ship.captain);
            tvShipName.setText(ship.name);
        }
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {

        private TextView tvGroupName;

        GroupViewHolder(View itemView) {
            super(itemView);
            this.tvGroupName = itemView.findViewById(R.id.tv_ship_type);
        }

        void onBind(String groupName) {
            tvGroupName.setText(groupName);
        }
    }

}
