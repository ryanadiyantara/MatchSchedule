package com.example.premierleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import adapter.MatchAdapter;
import db.DbHelper;
import model.Match;

public class Mainmenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private ArrayList<Match> orderArrayList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new MatchAdapter(this);

        dbHelper = new DbHelper(this);
        orderArrayList = dbHelper.readMatch();
        adapter.setListMatch(orderArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Mainmenu.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderArrayList = dbHelper.readMatch();
        adapter.setListMatch(orderArrayList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.createnewitem){
            startActivity(new Intent(this, Create.class));
        } else if (item.getItemId() == R.id.logout) {
            startActivity(new Intent(this, Login.class));
        }
        return true;
    }
}