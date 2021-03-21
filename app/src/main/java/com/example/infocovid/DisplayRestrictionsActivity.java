package com.example.infocovid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class DisplayRestrictionsActivity extends Activity {

    ExpandableListView restrictionsView;
    RestrictionsClient restrictionsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_restrictions);
        restrictionsView = (ExpandableListView) findViewById(R.id.expandableListView);

        RestrictionsClient restrictionsClient = new RestrictionsClient(restrictionsView, this);
        restrictionsClient.execute();
    }
}