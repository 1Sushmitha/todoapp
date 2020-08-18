package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT="item text";
    public static final String KEY_ITEM_POSITION = "item position";
    public static final int EDIT_TEXT_CODE=20;



    List<String> items;
    Button adItem;
    EditText editText;
    RecyclerView rcView;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adItem = findViewById(R.id.adItem);

        editText =findViewById(R.id.addText);
        rcView=findViewById(R.id.rcView);

        loadItems();
/*
        items = new LinkedList<String>(Arrays.asList("buy milk","goto the gym","have a fun"));
*/
        ItemsAdapter.OnLongClickListener onLongClickListener= new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //delete the item frm thr model
                items.remove(position);
                //notify the item when it is removed
                itemsAdapter.notifyItemRemoved(position);
                saveItems();
            }
        };
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int postion) {
                Intent i= new Intent(MainActivity.this,editActivity.class);
                // pass the edit
                i.putExtra(KEY_ITEM_TEXT,items.get(postion));
                i.putExtra(KEY_ITEM_POSITION,postion);
                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };
         itemsAdapter= new ItemsAdapter(items,onLongClickListener,onClickListener);
        rcView.setAdapter(itemsAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        //3rd step add items to the list
        adItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String newItem= editText.getText().toString();
              //add items to the list
                items.add(newItem);
                //notify adapter that an item added
                itemsAdapter.notifyItemInserted(items.size()-1);
                editText.setText("");
                Toast.makeText(MainActivity.this,"item inserted",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }
    //handle the result of thr edit activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // retieve the updated value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            // extact the position of the edited item from the key  position
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            // update the item at the right position
            items.set(position,itemText);
            //notify the adapter
            itemsAdapter.notifyItemChanged(position);
            //persist the item read write to
            saveItems();
            Toast.makeText(getApplicationContext(),"updated the items",Toast.LENGTH_SHORT).show();

        } else {
            Log.w("MainActivity", "Unknown cl to onActivityResult");
        }


    }

    private  File getDataFile()
   {
       return new File(getFilesDir(), "data_file");
   }
    // read the items
    private void loadItems()
    {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("mainActivity","error reading items", e);
            items=new ArrayList<>();
        }
    }
    //writing the items into file
    private void saveItems()
    {
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("mainActivity","error reading items", e);
        }

    }


    }

