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

                //FetchKidneyTask kidneyTask = new FetchKidneyTask(context);
                //kidneyTask.execute("fikimiki");

                final String FORECAST_BASE_URL =
                        "http://api.nal.usda.gov/ndb/search/?format=json&";
                final String FORMAT_PARAM = "format"; // format to json - linia 293
                final String QUERY_PARAM = "q";
                final String SORT_PARAM = "sort"; //tylko co to jest ten sort?
                final String MAX_PARAM = "cnt";
                final String OFFSET_PARAM = "offset"; //co to robi?
                final String APPID_PARAM = "APPID";

                String forecastJsonStr = null;

                String format = "json";
                String units = "metric";
                String sort = "n";
                int numDays = 14;
                int maxPositions = 5;
                int offset = 0;

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(QUERY_PARAM, foodName) //to jest ta nazwa wpisana
                        .appendQueryParameter(SORT_PARAM, sort)
                        .appendQueryParameter(MAX_PARAM, Integer.toString(maxPositions))
                        .appendQueryParameter(OFFSET_PARAM, Integer.toString(offset))
                        .appendQueryParameter(APPID_PARAM, BuildConfig.NDB_API_KEY)
                        .build();

                Toast.makeText(context, foodName, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ChooseTheMeal.class);
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
