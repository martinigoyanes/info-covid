package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import com.example.infocovid.NewsClient;
public class DisplayNewsActivity extends AppCompatActivity {

    private RecyclerView newsView;
    private ArrayList<Article> articles = new ArrayList<Article>();

    private NewsAdapter newsAdapter;
    private LinearLayoutManager newsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        newsView = (RecyclerView) findViewById(R.id.recView);

        newsLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsView.setLayoutManager(newsLayout);

        NewsClient newsClient = new NewsClient(newsView);
        newsClient.execute();

    }

    public void webView(View view){
        System.out.println("webView");
    }
}