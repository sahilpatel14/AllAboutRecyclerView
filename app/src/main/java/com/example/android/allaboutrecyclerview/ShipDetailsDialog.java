package com.example.android.allaboutrecyclerview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.android.allaboutrecyclerview.data.models.Ship;

/**
 * Created by sahil-mac on 01/05/18.
 */

public class ShipDetailsDialog extends DialogFragment {

    private Ship ship;

    public static ShipDetailsDialog newInstance(Ship ship) {
        ShipDetailsDialog dialog = new ShipDetailsDialog();
        dialog.ship = ship;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_ship_details, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        TextView textView = rootView.findViewById(R.id.dialog_text);
        Context context = getContext();
        if (context != null && ship != null){
            String text = getContext().getString(
                    R.string.ship_description_x,ship.name,ship.captain, ship.length, ship.type, ship.weapons,ship.name, ship.firstAppearance);
            textView.setText(text);
        }


        rootView.findViewById(R.id.dialog_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null){
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }
}
