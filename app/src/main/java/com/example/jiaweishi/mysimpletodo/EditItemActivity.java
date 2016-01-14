package com.example.jiaweishi.mysimpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        index = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_ITEM_ID, -1);
        String content = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_ITEM_CONTENT);

        EditText editText = (EditText) findViewById(R.id.etNewContent);
        editText.setText(content);
    }

    public void onClickSaveChange(View view){
        EditText editText = (EditText) findViewById(R.id.etNewContent);
        String newContent = editText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_MESSAGE_ITEM_ID, index);
        intent.putExtra(MainActivity.EXTRA_MESSAGE_ITEM_CONTENT, newContent);
        setResult(RESULT_OK, intent);
        finish();
    }
}
