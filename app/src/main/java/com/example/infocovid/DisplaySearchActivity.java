package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

public class DisplaySearchActivity extends AppCompatActivity {
    SearchView searchBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);

        searchBox = (SearchView) findViewById(R.id.searchBox);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getInput(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void getInput(String searchedProvince)
    {
        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        intent.putExtra("province", searchedProvince);
        startActivity(intent);
    }

    public void loadMadrid(View view){
        Log.d("DisplaySearchAtivity","Loading Madrid restrictions");
        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        intent.putExtra("province", "madrid");
        startActivity(intent);
    }
    public void loadBarcelona(View view){
        Log.d("DisplaySearchAtivity","Loading Barcelona restrictions");
        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        intent.putExtra("province", "barcelona");
        startActivity(intent);
    }
    public void loadCoruña(View view){
        Log.d("DisplaySearchAtivity","Loading Coruña restrictions");
        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        intent.putExtra("province", "a+coruña");
        startActivity(intent);
    }
}