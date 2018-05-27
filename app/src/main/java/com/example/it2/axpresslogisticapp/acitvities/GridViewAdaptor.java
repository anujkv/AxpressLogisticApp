package com.example.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it2.axpresslogisticapp.R;

/**
 * Created by IT2 on 5/25/2018.
 */

class GridViewAdaptor extends BaseAdapter {
    private Context mContext;
    private final String[] string;
    private final int[] Iconid;

    GridViewAdaptor(Context mContext, String[] string, int[] iconid) {
        this.mContext = mContext;
        this.string = string;
        Iconid = iconid;
    }


    @Override
    public int getCount() {
        return string.length;
    }

    @Override
    public Object getItem(int i) { return null; }

    @Override
    public long getItemId(int i) {
        Toast.makeText(mContext,"clicked getItemId "+ i,Toast.LENGTH_SHORT).show();
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.homepage_grid_item, null);
            TextView textView = grid.findViewById(R.id.grid_item_textviewId);
            ImageView imageView = grid.findViewById(R.id.grid_item_iconId);
            textView.setText(string[i]);
            imageView.setImageResource(Iconid[i]);
        } else {
            grid = view;
        }
        return grid;
    }
}
