package com.example.jiaweishi.mysimpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        index = intent.getIntExtra(MainActivity.EXTRA_MESSAGE_ITEM_ID, -1);
        String title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_ITEM_TITLE);
        String priority = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_ITEM_PRIORITY);

        TextView tvItemTitle = (TextView) findViewById(R.id.tvItemTitle);
        tvItemTitle.setText(title);

        EditText eTItemPriority = (EditText) findViewById(R.id.etNewPriority);
        eTItemPriority.setText(priority);
    }

    public void onClickSaveChange(View view){
        EditText eTItemPriority = (EditText) findViewById(R.id.etNewPriority);
        String newPriority = eTItemPriority.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_MESSAGE_ITEM_ID, index);
        intent.putExtra(MainActivity.EXTRA_MESSAGE_ITEM_PRIORITY, newPriority);
        setResult(RESULT_OK, intent);
        finish();
    }
}
