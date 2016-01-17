package com.example.jiaweishi.mysimpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_ITEM_ID = "extra_message_item_id";
    public static final String EXTRA_MESSAGE_ITEM_TITLE = "extra_message_item_title";
    public static final String EXTRA_MESSAGE_ITEM_PRIORITY = "extra_message_item_priority";
    public static final int REQUEST_CODE = 200;

    List<Item> items;
    ItemAdapter itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();

        itemsAdapter = new ItemAdapter(this, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            //String itemTitle = data.getStringExtra(EXTRA_MESSAGE_ITEM_TITLE);
            String newPriority = data.getStringExtra(EXTRA_MESSAGE_ITEM_PRIORITY);
            int index = data.getIntExtra(EXTRA_MESSAGE_ITEM_ID, -1);

            if(index != -1){
                Item currItem = items.get(index);
                Item newItem = new Item(currItem.getTitle(), newPriority);
                items.set(index, newItem);
                itemsAdapter.notifyDataSetChanged();

            }
        }
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Item newItem = new Item(itemText, "Medium");
        itemsAdapter.add(newItem);
        etNewItem.setText("");
        ItemDatabaseHelper.getInstance(this).addItem(newItem);
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        deleteItem(i);
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startItemEditPage(i);
                    }
                }

        );
    }

    private void readItems(){
        ItemDatabaseHelper databaseHelper = ItemDatabaseHelper.getInstance(this);
        items = databaseHelper.readAllItems();
    }

    private void deleteItem(int index){
        Item itemToRemove = items.get(index);
        items.remove(index);
        itemsAdapter.notifyDataSetChanged();
        ItemDatabaseHelper.getInstance(this).deleteItem(itemToRemove);
    }

    private void startItemEditPage(int index){
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra(EXTRA_MESSAGE_ITEM_ID, index);
        intent.putExtra(EXTRA_MESSAGE_ITEM_TITLE, items.get(index).getTitle());
        intent.putExtra(EXTRA_MESSAGE_ITEM_PRIORITY, items.get(index).getPriority());
        startActivityForResult(intent, REQUEST_CODE);
    }
}
