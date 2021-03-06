package com.example.nextsteps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    ListView list1;
    Menu addnote_menu;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.nextstep", Context.MODE_PRIVATE);
        list1 = findViewById(R.id.list1);
        HashSet<String> set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null) {
            notes.add("Example Note");
        }
        else
        {
            notes=new ArrayList<>(set);

        }
        notes.add("speed note");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        list1.setAdapter(arrayAdapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Editor.class);
                intent.putExtra("noteid", i);
                startActivity(intent);
            }
        });
        list1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemtoDelete=i;
                new AlertDialog.Builder(MainActivity.this)
                     .setIcon(android.R.drawable.ic_dialog_alert)
                     .setTitle("Are you sure?")
                     .setMessage("Do you want to delete this note/Task")
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notes.remove(itemtoDelete);
                        arrayAdapter.notifyDataSetChanged();
                        HashSet<String> set=new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes",set).apply();
                    }
                })
                .setNegativeButton("No",null)
                .show();


                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnote_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getApplicationContext(),Editor.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}


