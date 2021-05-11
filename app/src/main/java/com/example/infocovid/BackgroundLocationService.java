package com.example.infocovid;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BackgroundLocationService extends Service {

    String channelId = "checkRestrictions";
    private LocationListener listener;
    private LocationManager locationManager;
    private static final String TAG = "BgLocationService";
    private final int LOCATION_INTERVAL = 10000; // 10000ms - min
    private final int LOCATION_DISTANCE = 50; //50m
    public static String toqueDeQueda;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                saveLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, listener);

        // Check list of Available Location Provides
        boolean soloActivos = true;
        List<String> proveedores = locationManager.getProviders(soloActivos);
        // Check if desirable provider is active
        String proveedorElegido = LocationManager.GPS_PROVIDER;
        boolean disponible = proveedores.contains(proveedorElegido);
        // Otherwise, use first available
        if (!disponible) {
            proveedorElegido = proveedores.get(0);
        }
        // Ask last known location to provider
        Location localizacion = locationManager.getLastKnownLocation(proveedorElegido);

        saveLocationInfo(localizacion);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(listener);
        }
    }

    private void saveLocationInfo(Location location){
        // Transformar lat y long en zipCode
        String zipCode = "";
        String comunidad = "";
        String pais = "";

        Geocoder geocoder = new Geocoder(BackgroundLocationService.this, Locale.getDefault());
        // Reverse Geocode coordiantes
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            zipCode = addresses.get(0).getPostalCode();
            // no saca la comunidad, saca la provincia como hacemos?
            comunidad = addresses.get(0).getSubAdminArea();
            pais = addresses.get(0).getCountryName();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        // Creamos colecciónn de preferencias
        String sharedPrefFile = "com.uc3m.it.infocovid";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String prev_comunidad = mPreferences.getString("comunidad", "null");
        String prev_pais = mPreferences.getString("pais", "null");

        SharedPreferences.Editor editor = mPreferences.edit();

        // Guardamos el valor de la preferencia
        editor.putString("zipCode", zipCode);
        editor.putString("prev_comunidad", prev_comunidad);
        editor.putString("comunidad", comunidad);
        editor.putString("pais", pais);
        editor.apply();

        Log.d(TAG, "LAT: " + location.getLatitude() + "\n LONG: "
                + location.getLongitude() + " \n ZIPCODE: " + zipCode);

        if(!comunidad.equals(prev_comunidad) && pais.equals("Spain")) {
            newRestrictionsNotification(comunidad, zipCode);
            Log.i("prueba toque", "Estás en " + comunidad.toLowerCase());
            RestrictionsClient restrictionsClient = new RestrictionsClient(getApplicationContext(), null, comunidad.toLowerCase(), false);
            restrictionsClient.execute();
        }

        // Broadcast Intent w/ Filter
        Intent intent = new Intent("location_update");
        intent.putExtra("address",zipCode + ", " + comunidad + ", " + pais + " (" + prev_comunidad + ")");
        sendBroadcast(intent);
    }

    public void newRestrictionsNotification(String comunidad, String zipCode) {
        //Creamos notificación
        NotificationManager notificationManager;
        // crea canal de notificaciones
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.getApplicationContext(), "com.uc3m.it.infocovid.notify_001");

        //pendingIntent para abrir la actividad cuando se pulse la notificación
        Intent intent = new Intent(this.getApplicationContext(), DisplayRestrictionsActivity.class);
        intent.putExtra("zipCode", zipCode);
        intent.putExtra("activity", "MainActivity");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent).setSmallIcon(R.drawable.ic_covid_notification);
        mBuilder.setContentTitle("Bienvenido a " + comunidad).setContentText("Revisa las restricciones de la zona.");
        mBuilder.setLights(Color.RED, 0, 1).setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal de New Restrictions",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        notificationManager.notify(0, mBuilder.build());

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentText("InfoCovid ejecutando en segundo plano.")
                .setSmallIcon(R.drawable.ic_covid_notification)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }
}


