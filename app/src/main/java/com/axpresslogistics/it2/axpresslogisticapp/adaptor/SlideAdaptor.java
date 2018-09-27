package com.axpresslogistics.it2.axpresslogisticapp.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.axpresslogistics.it2.axpresslogisticapp.R;
import com.axpresslogistics.it2.axpresslogisticapp.acitvities.BusinessCardView;

public class SlideAdaptor extends PagerAdapter {

    private Context context;
    private ImageView imageView;

    private LayoutInflater inflater;
    byte[] image = null;
    Bitmap bitmap;
    BusinessCardView businessCardViewInstance = new BusinessCardView();

    public SlideAdaptor(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public SlideAdaptor(Context context) {
        this.context = context;

    }
    public int[] list_images = {R.drawable.cardforntimage,R.drawable.cardbackimage};



    @Override
    public int getCount() {
        return list_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slidecard_view,container,false);
        ImageView imageslider = view.findViewById(R.id.bcardview);
        imageslider.setImageResource(list_images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
