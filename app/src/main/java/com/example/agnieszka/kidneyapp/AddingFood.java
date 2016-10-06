package com.example.agnieszka.kidneyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddingFood extends AppCompatActivity {

    Button clickToSearch;
    EditText typeToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        clickToSearch = (Button) findViewById(R.id.search_button);
        typeToSearch = (EditText) findViewById(R.id.search_for_food);

    }
}
