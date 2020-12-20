package com.example.yoitgeo_google;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SampleData> sampleData;

    public MyAdapter(Context context, ArrayList<SampleData> data) {
        mContext = context;
        sampleData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sampleData.size();
    }

    @Override
    public Object getItem(int i) {
        return sampleData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.custom_item, null);

        TextView tvRoomNum = (TextView) view.findViewById(R.id.tvRoomNumber);
        TextView tvRoomName = (TextView) view.findViewById(R.id.tvRoomName);

        tvRoomNum.setText(sampleData.get(position).getRoomNum());
        tvRoomName.setText(sampleData.get(position).getRoomName());

        return view;
    }
}
