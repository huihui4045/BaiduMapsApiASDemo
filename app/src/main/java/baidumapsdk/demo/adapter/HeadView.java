package baidumapsdk.demo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import baidumapsdk.demo.R;

public class HeadView extends FrameLayout {

    private int id;
    public HeadView(Context context,int resId) {
        super(context);

        View.inflate(context, R.layout.head,this);

        ((ImageView) findViewById(R.id.image_head)).setImageResource(resId);
    }
}
