//==================================================================================================
//
//      Title:          DisplayNewsActivity (InfoCovid)
//      Authors:        Martín Iglesias Goyanes
//                      Jacobo Del Castillo Monche
//                      Carlos García Guzman
//      Date:           12 May 2021
//
//==================================================================================================

package com.example.infocovid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayNewsActivity extends AppCompatActivity {

    private RecyclerView newsView;
    private LinearLayoutManager newsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        // Set Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.news_toolbar);
        setTitle("News");
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_go_back_arrow);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        newsView = (RecyclerView) findViewById(R.id.recView);

        newsLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsView.setLayoutManager(newsLayout);

        NewsClient newsClient = new NewsClient(newsView);
        newsClient.execute();
    }

}