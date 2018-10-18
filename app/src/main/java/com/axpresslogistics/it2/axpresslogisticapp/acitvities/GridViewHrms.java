package com.axpresslogistics.it2.axpresslogisticapp.acitvities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.axpresslogistics.it2.axpresslogisticapp.R;

public class GridViewHrms extends BaseAdapter {
    private Context mContext;
    private final String[] string;
    private final int[] Iconid;

    public GridViewHrms(Context mContext, String[] string, int[] iconid) {
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

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.hrms_grid_item, null);
            TextView textView = grid.findViewById(R.id.gridhrms_item_textviewId);
            ImageView imageView = grid.findViewById(R.id.gridhrms_item_iconId);
            textView.setText(string[i]);
            imageView.setImageResource(Iconid[i]);
        } else {
            grid = view;
        }
        return grid;
    }

    public interface onClick{
        public void Click(int position);
    }
}
