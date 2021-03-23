package com.example.infocovid;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BackgroundLocationService extends Service {
    private final LocationServiceBinder binder = new LocationServiceBinder();
    private static final String TAG = "BgLocationService";
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final int LOCATION_INTERVAL = 10000; // 10000ms - 1min
    private final int LOCATION_DISTANCE = 50; //50m

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class LocationListener implements android.location.LocationListener {
        private Location lastLocation = null;
        private final String TAG = "LocationListener";
        private Location mLastLocation;
        private String zipCode;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            // Transfrom longitude and latitude to zipCode and save it to disk
            saveZipCode(mLastLocation);

            // Check if curfew is 30 min close, if so we send a notification

            notifyCurfew();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + status);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //onTaskRemoved(intent);
        return START_STICKY;
    }

        @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(locationListener);
                //locationRepository = null;
            } catch (Exception ex) {
                Log.i(TAG, "fail to remove location listeners, ignore", ex);
            }
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "Kernel removed my backGroundService");
        Intent restartServiceIntent = new Intent(this, this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    private void initializeLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public void startTracking() {
        initializeLocationManager();
        locationListener = new LocationListener(LocationManager.GPS_PROVIDER);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);

        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "NETWORK provider does not exist " + ex.getMessage());
        }

    }


    public class LocationServiceBinder extends Binder {
        public BackgroundLocationService getService() {
            return BackgroundLocationService.this;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notifyCurfew() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo) //set icon for notification
                        .setContentTitle("Toque de queda cerca")
                        .setContentText("30 minutos para el toque de queda en tu zona!")
                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Curfew";
        NotificationChannel channel = new NotificationChannel(
                channelId,
                "curfewChannel",
                NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        builder.setChannelId(channelId);
        manager.notify(0, builder.build());
    }

    private void saveZipCode(Location location){
        // Transformar lat y long en zipCode
        String zipCode = "";
        Geocoder geocoder = new Geocoder(BackgroundLocationService.this, Locale.getDefault());
        // lat,lng, your current location
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            zipCode = addresses.get(0).getPostalCode();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        // Creamos colecciÃ³n de preferencias
        String sharedPrefFile = "com.uc3m.it.hellolocation";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        SharedPreferences.Editor editor = mPreferences.edit();

        // Guardamos el valor de la preferencia
        editor.putString("zipCode", zipCode);
        editor.apply();

        Toast.makeText(BackgroundLocationService.this, "LAT: " + location.getLatitude() + "\n LONG: "
                + location.getLongitude() + " \n ZIPCODE: " + zipCode, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "LAT: " + location.getLatitude() + "\n LONG: "
                + location.getLongitude() + " \n ZIPCODE: " + zipCode);
    }

    private void checkCurfew(){

    }
}


