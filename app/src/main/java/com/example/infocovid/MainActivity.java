package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadNews(View view){
        String msg = "Clickado en news";
        Log.d("MainActivity",msg);
        Intent intent = new Intent(this, DisplayNewsActivity.class);
        startActivity(intent);
    }
    public void loadRestrictions(View view){
        String msg = "Clickado en restrictions";
        Log.d("MainActivity",msg);
        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        startActivity(intent);
    }
    public void loadSearch(View view){
        String msg = "Clickado en search";
        Log.d("MainActivity",msg);
        Intent intent = new Intent(this, DisplaySearchActivity.class);
        startActivity(intent);
    }
}