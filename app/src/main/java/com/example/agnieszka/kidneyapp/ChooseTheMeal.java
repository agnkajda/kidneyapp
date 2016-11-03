package com.example.agnieszka.kidneyapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import com.example.agnieszka.kidneyapp.AddingFood;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseTheMeal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_the_meal);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ChooseTheMealFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    public static class ChooseTheMealFragment extends Fragment {

        private ArrayAdapter<String> mFood;
        //private ArrayList<String> mFood = new ArrayList<String>();

        public ChooseTheMealFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Add this line in order for this fragment to handle menu events.
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.testfragment, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_refresh) {
                String numberNDBO;
                Intent intent = getActivity().getIntent();
                /*if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                    numberNDBO = intent.getStringExtra(Intent.EXTRA_TEXT);
                }
                else {
                    numberNDBO = "01009";
                }*/
                numberNDBO = "01009";

                FetchValuesTask foodTask = new FetchValuesTask();
                foodTask.execute(numberNDBO);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            String[] data = {
                    "water",
                    "energy",
                    "sodium"
            };

            List<String> foodValues = new ArrayList<String>(Arrays.asList(data));

            mFood =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_forecast_3, // The name of the layout ID.
                            R.id.list_item_forecast_textview_3, // The ID of the textview to populate.
                            foodValues);

            View rootView = inflater.inflate(R.layout.activity_choose_the_meal_fragment, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast_3);
            listView.setAdapter(mFood);

            // The detail Activity called via intent.  Inspect the intent for forecast data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                String foodStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.values_text))
                        .setText(foodStr);
            }

            return rootView;
        }

        public class FetchValuesTask extends AsyncTask<String, Void, String[]> {

            private final String LOG_TAG = TestActivityFragment.FetchTask.class.getSimpleName();

            private String[] getValuesFromJson(String foodJsonStr, int maxPositions)
                    throws JSONException {

                final String NDB_REPORT = "report";
                final String NDB_FOOD = "food";
                final String NDB_NUTRIENTS = "nutrients";
                final String NDB_VALUE = "value";
                final String NDB_NAME = "name";

                final String water = "255";
                final String energy = "208";
                final String carbohydrate = "205";
                final String protein = "203";
                final String fat = "204";
                final String magnesium = "304";
                final String phosphorus = "305";
                final String potasium = "306";
                final String sodium = "307";

                JSONObject foodJson = new JSONObject(foodJsonStr);
                JSONObject report = foodJson.getJSONObject(NDB_REPORT);
                JSONObject food = report.getJSONObject(NDB_FOOD);
                JSONArray nutrientsArray = food.getJSONArray(NDB_NUTRIENTS);

                maxPositions = nutrientsArray.length();
                String[] resultStrs = new String[maxPositions];

                for (int i = 0; i < nutrientsArray.length(); i++) {
                    JSONObject foodValues = nutrientsArray.getJSONObject(i);
                    String type = foodValues.getString(NDB_NAME);
                    String value = foodValues.getString(NDB_VALUE);
                    resultStrs[i] = value + " - " + type;
                }
                return resultStrs;
            }

            @Override
            protected String[] doInBackground(String... params) {

                // If there's no zip code, there's nothing to look up.  Verify size of params.
                if (params.length == 0) {
                    return null;
                }

                // These two need to be declared outside the try/catch
                // so that they can be closed in the finally block.
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String foodJsonStr = null;

                String format = "json";
                String type = "b";
                int maxPositions = 7;

                try {
                    // Construct the URL for the OpenWeatherMap query
                    // Possible parameters are avaiable at OWM's forecast API page, at
                    // http://openweathermap.org/API#forecast

                    final String FORECAST_BASE_URL =
                            "http://api.nal.usda.gov/ndb/reports/?";
                    final String FORMAT_PARAM = "format";
                    final String TYPE_PARAM = "type";
                    final String NDBO_PARAM = "ndbno";
                    final String APPID_PARAM = "api_key";

                    Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                            .appendQueryParameter(NDBO_PARAM, params[0])
                            .appendQueryParameter(TYPE_PARAM, type)
                            .appendQueryParameter(FORMAT_PARAM, format)
                            .appendQueryParameter(APPID_PARAM, BuildConfig.NDB_API_KEY)
                            .build();

                    URL url = new URL(builtUri.toString());

                    Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    foodJsonStr = buffer.toString();
                    Log.v(LOG_TAG, "Food string: " + foodJsonStr);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attemping
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }

                try {
                    return getValuesFromJson(foodJsonStr, maxPositions);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                // This will only happen if there was an error getting or parsing the forecast.
                return null;
            }

            @Override
            protected void onPostExecute(String[] result) {
                if (result != null) {
                    mFood.clear();
                    for (String dayForecastStr : result) {
                        mFood.add(dayForecastStr);
                        Log.d("My array list content: ", dayForecastStr);                    }

                }
            }


        }

    }
}
