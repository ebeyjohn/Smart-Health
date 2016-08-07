package com.tcs.smarthealth;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ebey John on 8/4/2016.
 */
public class CoustomAdapter extends BaseAdapter {
    private Context mCOntext;
    private List<Pair<Integer, String>> mData;
    public CoustomAdapter(Context context,List<Pair<Integer,String>> data){
        this.mCOntext=context;
        this.mData=data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Pair<Integer,String> getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(mCOntext).inflate(android.R.layout.simple_list_item_activated_1, parent, false);

        String text = getItem(position).second;

        ((TextView) view).setText(text);

        return view;

    }
}
