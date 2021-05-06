package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class DisplaySearchActivity extends AppCompatActivity {

    SearchView searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);

        // Set Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setTitle("Search");
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_go_back_arrow);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });


        // Set Search Box
        searchBox = (SearchView) findViewById(R.id.searchBox);
        searchBox.clearFocus();
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


        // Set Buttons
        Button andalucia = (Button) findViewById(R.id.button_andalucia);
        andalucia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_andalucia);
                intent.putExtra("id", "andalucia");
                startActivity(intent);
            }
        });

        Button aragon = (Button) findViewById(R.id.button_aragon);
        aragon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_aragon);
                intent.putExtra("id", "aragon");
                startActivity(intent);
            }
        });

        Button canarias = (Button) findViewById(R.id.button_canarias);
        canarias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplaySearchActivity.this, Pop.class);
                intent.putExtra("layout", R.layout.popup_canarias);
                intent.putExtra("id", "canarias");
                startActivity(intent);
            }
        });

        Button cantabria = (Button) findViewById(R.id.button_cantabria);
        cantabria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Cantabria restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "cantabria");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button cLeon = (Button) findViewById(R.id.button_cLeon);
        cLeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_leon);
                intent.putExtra("id", "cLeon");
                startActivity(intent);
            }
        });

        Button cMancha = (Button) findViewById(R.id.button_cMancha);
        cMancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_mancha);
                intent.putExtra("id", "cMancha");
                startActivity(intent);
            }
        });

        Button cataluña = (Button) findViewById(R.id.button_cataluña);
        cataluña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_cataluna);
                intent.putExtra("id", "cataluña");
                startActivity(intent);
            }
        });

        Button madrid = (Button) findViewById(R.id.button_madrid);
        madrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Madrid restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "madrid");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button valencia = (Button) findViewById(R.id.button_valencia);
        valencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_valencia);
                intent.putExtra("id", "valencia");
                startActivity(intent);
            }
        });

        Button extremadura = (Button) findViewById(R.id.button_extremadura);
        extremadura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_extremadura);
                intent.putExtra("id", "extremadura");
                startActivity(intent);
            }
        });

        Button galicia = (Button) findViewById(R.id.button_galicia);
        galicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_galicia);
                intent.putExtra("id", "galicia");
                startActivity(intent);
            }
        });

        Button baleares = (Button) findViewById(R.id.button_baleares);
        baleares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Baleares restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "baleares");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button rioja = (Button) findViewById(R.id.button_rioja);
        rioja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Rioja restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "la+rioja");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button navarra = (Button) findViewById(R.id.button_navarra);
        navarra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Navarra restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "navarra");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button paisVasco = (Button) findViewById(R.id.button_paisvasco);
        paisVasco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pop.class);
                intent.putExtra("layout", R.layout.popup_paisvasco);
                intent.putExtra("id", "paisvasco");
                startActivity(intent);
            }
        });

        Button asturias = (Button) findViewById(R.id.button_asturias);
        asturias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Asturias restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "asturias");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button murcia = (Button) findViewById(R.id.button_murcia);
        murcia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Murcia restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "murcia");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button ceuta = (Button) findViewById(R.id.button_ceuta);
        ceuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading Ceuta restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "ceuta");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });

        Button melilla = (Button) findViewById(R.id.button_melilla);
        melilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DisplaySearchAtivity", "Loading melilla restrictions");
                Intent intent = new Intent(DisplaySearchActivity.this, DisplayRestrictionsActivity.class);
                intent.putExtra("province", "melilla");
                intent.putExtra("activity", "DisplaySearchActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
            }
        });
    }

    public void getInput(String searchedProvince){
        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        Boolean numeric = true;
        try{
            Integer.parseInt(searchedProvince);
            numeric = true;
        } catch(NumberFormatException e){
            numeric = false;
        }
        if(numeric){
            intent.putExtra("zipCode", searchedProvince);
            Log.d("ZIPCODE",searchedProvince);
        } else{
            intent.putExtra("province", searchedProvince);
            Log.d("PROVINCE",searchedProvince);
        }
        intent.putExtra("activity", "DisplaySearchActivity");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
    }

}