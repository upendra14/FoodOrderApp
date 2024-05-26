package com.android.foodorderapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.foodorderapp.adapters.RestaurantListAdapter;
import com.android.foodorderapp.model.RestaurantModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");

        List<RestaurantModel> restaurantModelList =  getRestaurantData();

        initRecyclerView(restaurantModelList); //RETRIEVE THE RESTAURANT DATA
    }

    private void initRecyclerView(List<RestaurantModel> restaurantModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RestaurantListAdapter adapter = new RestaurantListAdapter(restaurantModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<RestaurantModel> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.restaurent); //This line opens an input stream to read the restaurant data from a raw resource file.
        Writer writer = new StringWriter(); //creates a writer to write the data read from the input stream.
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString(); //converts the data written to the writer into a string, representing the JSON data.
        Gson gson = new Gson(); //This line creates a Gson object, which is a JSON serialization and deserialization library provided by Google.
        RestaurantModel[] restaurantModels =  gson.fromJson(jsonStr, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);

        return  restList;  //returns the list of RestaurantModel objects retrieved from the JSON data.

    }

    @Override
    public void onItemClick(RestaurantModel restaurantModel) { //defines the onItemClick method, which is called when an item in the RecyclerView is clicked.
        Intent intent = new Intent(MainActivity.this, RestaurantMenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);

    }
}