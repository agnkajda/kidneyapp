package com.example.agnieszka.kidneyapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AddingFood extends AppCompatActivity {

    Button clickToSearch;
    EditText typeToSearch;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_food);

        clickToSearch = (Button) findViewById(R.id.search_button);
        typeToSearch = (EditText) findViewById(R.id.search_for_food);

        //setHasOptionsMenu(true);

        View.OnClickListener clicking = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = getApplicationContext();
                Intent intent = new Intent(context, ChooseTheMeal.class);
                startActivity(intent);

                FetchKidneyTask kidneyTask = new FetchKidneyTask(context); //musimy tu przesłać nazwę produktu - czemu context?
                kidneyTask.execute();

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
