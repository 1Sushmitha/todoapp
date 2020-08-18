package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editActivity extends AppCompatActivity {

    Button savebtn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        savebtn=findViewById(R.id.savebtn);
        editText=findViewById(R.id.editText);

        getSupportActionBar().setTitle("Edit Item");

        editText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
  // when the user is done editing , serclick the save button.
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent
                Intent intent = new Intent();
                //pass the date(result of editing)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION,getIntent().getIntArrayExtra(MainActivity.KEY_ITEM_POSITION));
                //set the result of the intent
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}