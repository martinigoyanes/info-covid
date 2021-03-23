package com.example.infocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSION_REQUEST_CODE = 200;
    public BackgroundLocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //prepare location service
        final Intent intent = new Intent(this.getApplication(), BackgroundLocationService.class);
        this.getApplication().startService(intent);
        this.getApplication().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    public void loadNews(View view){
        String msg = "Clickado en news";
        Log.d("MainActivity",msg);
        Intent intent = new Intent(this, DisplayNewsActivity.class);
        startActivity(intent);
    }
    public void loadRestrictions(View view){
        String msg = "Clickado en restrictions";

        // Creamos coleccion de preferencias
        String sharedPrefFile = "com.uc3m.it.infocovid";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String zipCode = mPreferences.getString("zipCode", "0");
        Log.d("MainActivity",msg + " ZIPCODE: " + zipCode);

        Intent intent = new Intent(this, DisplayRestrictionsActivity.class);
        intent.putExtra("zipCode", zipCode);
        startActivity(intent);
    }
    public void loadSearch(View view){
        String msg = "Clickado en search";
        Log.d("MainActivity",msg);
        Intent intent = new Intent(this, DisplaySearchActivity.class);
        startActivity(intent);
    }

    public void startTracking() {
        //check for permission
        if (ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationService.startTracking();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTracking();
            }
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("BackgroundLocationService")) {
                locationService = ((BackgroundLocationService.LocationServiceBinder) service).getService();
                startTracking();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("BackgroundLocationService")) {
                locationService = null;
            }
        }
    };
}