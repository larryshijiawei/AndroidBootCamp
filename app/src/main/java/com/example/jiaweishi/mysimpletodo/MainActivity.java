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

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE_ITEM_ID = "extra_message_id";
    public static final String EXTRA_MESSAGE_ITEM_CONTENT = "extra_message_content";
    public static final int REQUEST_CODE = 200;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);

        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String newContent = data.getStringExtra(EXTRA_MESSAGE_ITEM_CONTENT);
            int index = data.getIntExtra(EXTRA_MESSAGE_ITEM_ID, -1);

            if(index != -1){
                items.set(index, newContent);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items.remove(i);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
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
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (Exception e){
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startItemEditPage(int index){
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra(EXTRA_MESSAGE_ITEM_ID, index);
        intent.putExtra(EXTRA_MESSAGE_ITEM_CONTENT, items.get(index));
        startActivityForResult(intent, REQUEST_CODE);
    }
}
