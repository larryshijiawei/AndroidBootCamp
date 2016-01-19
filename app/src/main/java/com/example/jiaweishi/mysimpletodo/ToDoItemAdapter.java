package com.example.jiaweishi.mysimpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jiaweishi on 1/16/16.
 */
public class ToDoItemAdapter extends ArrayAdapter<Item> {

    public ToDoItemAdapter(Context context, List<Item> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView tvName= (TextView) convertView.findViewById(R.id.tvItemName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvItemPriority);

        tvName.setText(item.getTitle());
        tvPriority.setText(item.getPriority());

        return convertView;

    }
}
