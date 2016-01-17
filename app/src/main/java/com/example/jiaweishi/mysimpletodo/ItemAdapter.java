package com.example.jiaweishi.mysimpletodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jiaweishi on 1/16/16.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, List<Item> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView tvName= (TextView) convertView.findViewById(R.id.itemName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.itemPriority);

        tvName.setText(item.getTitle());
        tvPriority.setText(item.getPriority());

        return convertView;

    }
}
