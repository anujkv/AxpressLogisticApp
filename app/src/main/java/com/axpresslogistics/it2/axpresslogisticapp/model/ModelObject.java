package com.axpresslogistics.it2.axpresslogisticapp.model;


public class ModelObject {

    private int titleResId;
    private int mLayoutResId;

//    RED(R.string.red, R.layout.view_red),
//    BLUE(R.string.blue, R.layout.view_blue),
//    GREEN(R.string.green, R.layout.view_green);

    ModelObject(int titleResId, int layoutResId) {
        titleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}