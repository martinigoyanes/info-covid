package com.example.infocovid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        int layout = getIntent().getIntExtra("layout", -1);
        String id = bundle.getString("id");
        setContentView(layout);

        // Set Minimized Activity
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        getWindow().setLayout((int) (w*0.85), (int) (h*0.8));
        // Set Andalucia Buttons
        if(id.equals("andalucia")) {
            Button almeria = (Button) findViewById(R.id.button_almeria);
            almeria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Almeria restrictions");
                    Intent intent = new Intent(getApplicationContext(), DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "almeria");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button cadiz = (Button) findViewById(R.id.button_cadiz);
            cadiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Cadiz restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "cadiz");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button cordoba = (Button) findViewById(R.id.button_cordoba);
            cordoba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Cordoba restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "cordoba");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button granada = (Button) findViewById(R.id.button_granada);
            granada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Granada restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "granada");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button huelva = (Button) findViewById(R.id.button_huelva);
            huelva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Huelva restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "huelva");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button jaen = (Button) findViewById(R.id.button_jaen);
            jaen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Jaen restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "jaen");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button malaga = (Button) findViewById(R.id.button_malaga);
            malaga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Malaga restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "malaga");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button sevilla = (Button) findViewById(R.id.button_sevilla);
            sevilla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Sevilla restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "sevilla");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("aragon")) {
            // Set Aragon Buttons
            Button huesca = (Button) findViewById(R.id.button_huesca);
            huesca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Huesca restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "huesca");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button teruel = (Button) findViewById(R.id.button_teruel);
            teruel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Teruel restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "teruel");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button zaragoza = (Button) findViewById(R.id.button_zaragoza);
            zaragoza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Zaragoza restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "zaragoza");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("canarias")) {
            // Set Canarias Buttons
            Button palmas = (Button) findViewById(R.id.button_palmas);
            palmas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Palmas restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "las+palmas");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button tenerife = (Button) findViewById(R.id.button_tenerife);
            tenerife.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Tenerife restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "santa+cruz+de+tenerife");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("cLeon")) {
            // Set Castilla y Leon Buttons
            Button avila = (Button) findViewById(R.id.button_avila);
            avila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Avila restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "avila");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button burgos = (Button) findViewById(R.id.button_burgos);
            burgos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchActivity", "Loading Burgos restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "burgos");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button leon = (Button) findViewById(R.id.button_leon);
            leon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchActivity", "Loading Leon restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "leon");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button palencia = (Button) findViewById(R.id.button_palencia);
            palencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchActivity", "Loading Palencia restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "palencia");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button salamanca = (Button) findViewById(R.id.button_salamanca);
            salamanca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Salamanca restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "salamanca");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button segovia = (Button) findViewById(R.id.button_segovia);
            segovia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Segovia restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "segovia");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button soria = (Button) findViewById(R.id.button_soria);
            soria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Soria restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "soria");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button valladolid = (Button) findViewById(R.id.button_valladolid);
            valladolid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Valladolid restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "valladolid");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button zamora = (Button) findViewById(R.id.button_zamora);
            zamora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchActivity", "Loading Zamora restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "zamora");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("cMancha")) {
            // Set Castilla - La Mancha Buttons
            Button albacete = (Button) findViewById(R.id.button_albacete);
            albacete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Albacete restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "albacete");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button ciudadreal = (Button) findViewById(R.id.button_ciudadreal);
            ciudadreal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Ciudad Real restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "ciudad+real");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button cuenca = (Button) findViewById(R.id.button_cuenca);
            cuenca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Cuenca restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "cuenca");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button guadalajara = (Button) findViewById(R.id.button_guadalajara);
            guadalajara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Guadalajara restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "guadalajara");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button toledo = (Button) findViewById(R.id.button_toledo);
            toledo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Toledo restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "toledo");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("cataluña")) {
            // Set Cataluña Buttons
            Button barcelona = (Button) findViewById(R.id.button_barcelona);
            barcelona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Barcelona restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "barcelona");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button gerona = (Button) findViewById(R.id.button_gerona);
            gerona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Gerona restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "girona");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button lerida = (Button) findViewById(R.id.button_lerida);
            lerida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Lerida restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "lleida");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button tarragona = (Button) findViewById(R.id.button_tarragona);
            tarragona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Tarragona restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "tarragona");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("valencia")) {
            // Set Valencia Buttons
            Button alicante = (Button) findViewById(R.id.button_alicante);
            alicante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Alicante restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "alicante");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button castellon = (Button) findViewById(R.id.button_castellon);
            castellon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Castellon restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "castellon");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button valencia = (Button) findViewById(R.id.button_valencia);
            valencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Valencia restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "valencia");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("extremadura")) {
            // Set Extremadura Buttons
            Button badajoz = (Button) findViewById(R.id.button_badajoz);
            badajoz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchActivity", "Loading Badajoz restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "badajoz");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button caceres = (Button) findViewById(R.id.button_caceres);
            caceres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchActivity", "Loading Caceres restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "caceres");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("galicia")) {
            // Set Galicia Buttons
            Button coruña = (Button) findViewById(R.id.button_coruña);
            coruña.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Coruña restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "a+coruña");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button lugo = (Button) findViewById(R.id.button_lugo);
            lugo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Lugo restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "lugo");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button orense = (Button) findViewById(R.id.button_orense);
            orense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Orense restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "ourense");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button pontevedra = (Button) findViewById(R.id.button_pontevedra);
            pontevedra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Pontevedra restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "pontevedra");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        } else if(id.equals("paisvasco")) {
            // Set Pais Vasco Buttons
            Button alava = (Button) findViewById(R.id.button_alava);
            alava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Alava restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "alava");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button guipuzcua = (Button) findViewById(R.id.button_guipuzcua);
            guipuzcua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Guipuzcua restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "guipuzcua");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });

            Button vizcaya = (Button) findViewById(R.id.button_vizcaya);
            vizcaya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DisplaySearchAtivity", "Loading Vizcaya restrictions");
                    Intent intent = new Intent(Pop.this, DisplayRestrictionsActivity.class);
                    intent.putExtra("province", "vizcaya");
                    intent.putExtra("activity", "DisplaySearchActivity");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
                }
            });
        }
    }
}
