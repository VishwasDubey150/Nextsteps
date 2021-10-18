package com.example.nextsteps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class Editor extends AppCompatActivity {
    EditText note;
    int noteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        note=findViewById(R.id.note);
        Intent intent=getIntent();
        noteid=intent.getIntExtra("noteid",-1);

        if(noteid!=-1)
        {
            note.setText(MainActivity.notes.get(noteid));
        }
        else {
            MainActivity.notes.add("");
            noteid = MainActivity.notes.size() - 1;
        }

            note.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.notes.set(noteid, String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.nextstep", Context.MODE_PRIVATE);
                    HashSet<String> set=new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes",set).apply();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }

            });
        }

    }


