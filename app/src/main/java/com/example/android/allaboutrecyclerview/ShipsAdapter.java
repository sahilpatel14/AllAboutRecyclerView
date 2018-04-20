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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sahil-mac on 20/04/18.
 */

public class ShipsAdapter extends RecyclerView.Adapter<ShipsAdapter.ShipViewHolder>{

    private final List<Ship> ships = new ArrayList<>();

    @NonNull
    @Override
    public ShipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ship, parent, false);
        return new ShipViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipViewHolder holder, int position) {
        holder.onBind(ships.get(position));
    }

    @Override
    public int getItemCount() {
        return ships.size();
    }

    public void setShips(List<Ship> newShips) {
        this.ships.clear();
        this.ships.addAll(newShips);
        notifyDataSetChanged();
    }

    static class ShipViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivShipImage;
        private TextView tvShipName;
        private TextView tvCaptainName;

        public ShipViewHolder(View itemView) {
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

}
