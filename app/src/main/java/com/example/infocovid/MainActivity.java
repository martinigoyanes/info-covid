package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadNews(View view){
        String msg = "Clickado en mi polla gorda";
        System.out.println(msg);
    }
    public void loadRestrictions(View view){
        String msg = "Clickado en restrictions";
        System.out.println(msg);
    }
    public void loadSearch(View view){
        String msg = "Clickado en search";
        System.out.println(msg);
    }
}