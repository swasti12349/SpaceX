package com.sro.spacex;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    rAdapter adapter;
    List<Model> list;
    LinearLayoutManager manager;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        //Room Database
        database = RoomDB.getInstance(this);
        list = database.mainDao().getAll();


        //RecyclerView
        manager = new LinearLayoutManager(this);
        adapter = new rAdapter(this, list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        FetchData(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuitem, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh: FetchData(MainActivity.this);
            case R.id.deleteall:  database.mainDao().reset(list);
                database.clearAllTables();
                list.clear();
                list.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
        }
        return true;
    }

    private void FetchData(Context context) {
        String url = "https://api.spacexdata.com/v4/crew";
        RequestQueue queue = Volley.newRequestQueue(context);
        recyclerView.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = response.getJSONObject(i);
                    String name = object.getString("name");
                    String agency = object.getString("agency");
                    String image = object.getString("image");
                    String wikipedia = object.getString("wikipedia");
                    String status = object.getString("status");
                    String id = object.getString("id");
                    Model data = new Model();
                    data.setName(name);
                    data.setAgency(agency);
                    data.setImage(image);
                    data.setStatus(status);
                    data.setWikipedia(wikipedia);
                    data.setID(id);
                    database.mainDao().insert(data);
                }
                list.clear();
                list.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonArrayRequest);
    }





}
