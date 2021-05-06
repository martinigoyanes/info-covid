package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

public class DisplayRestrictionsActivity extends AppCompatActivity {

    ExpandableListView restrictionsView;
    RestrictionsClient restrictionsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the zip code for the restrictions
        Bundle bundle = getIntent().getExtras();

        String zipCode = bundle.getString("zipCode");
        if(zipCode != null)
            Log.d("DisplayRestrictions", "Loading restrictions for " + zipCode);

        String province = bundle.getString("province");
        if(province != null) {
            province.toLowerCase().replace(" ", "+");
            Log.d("DisplayRestrictions", "Loading restrictions for " + province);
        }

        setContentView(R.layout.activity_display_restrictions);

        // Set Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.restrictions_toolbar);
        setTitle("Restrictions");
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_go_back_arrow);
        String previousActivity = "com.example.infocovid."+bundle.getString("activity");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), Class.forName(previousActivity));
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                overridePendingTransition(0, R.anim.slide_out_down);
            }
        });

        restrictionsView = (ExpandableListView) findViewById(R.id.expandableListView);
        restrictionsClient = new RestrictionsClient(restrictionsView, this, zipCode, province);
        restrictionsClient.execute();
    }
}