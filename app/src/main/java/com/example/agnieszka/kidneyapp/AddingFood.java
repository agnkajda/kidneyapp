package com.example.agnieszka.kidneyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddingFood extends AppCompatActivity {

    Button clickToSearch;
    EditText typeToSearch;
    Context context;
    //private ArrayAdapter<String> mKidneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        clickToSearch = (Button) findViewById(R.id.search_button);
        typeToSearch = (EditText) findViewById(R.id.search_for_food);
final String string;

        //setHasOptionsMenu(true);

        View.OnClickListener clicking = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = getApplicationContext();
                String foodName = typeToSearch.toString();

                Intent intent = new Intent(context, TestActivity.class);
                startActivity(intent);
            }
        };


        clickToSearch.setOnClickListener(clicking);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
