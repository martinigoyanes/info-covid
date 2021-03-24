package com.example.infocovid;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

public class BackgroundLocationService extends Service {
    private final LocationServiceBinder binder = new LocationServiceBinder();
    private static final String TAG = "BgLocationService";
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final int LOCATION_INTERVAL = 10000; // 10000ms - min
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

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            // Transfrom longitude and latitude to zipCode and save it to disk
            saveLocationInfo(mLastLocation);

            // Launch curfew notification client
            NotificationClient notificationClient = new NotificationClient(getApplicationContext());
            notificationClient.execute();
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


    private void saveLocationInfo(Location location){
        // Transformar lat y long en zipCode
        String zipCode = "";
        String comunidad = "";
        Geocoder geocoder = new Geocoder(BackgroundLocationService.this, Locale.getDefault());
        // Reverse Geocode coordiantes
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            zipCode = addresses.get(0).getPostalCode();
            // no saca la comunidad, saca la provincia como hacemos?
            comunidad = addresses.get(0).getSubAdminArea();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        // Creamos colecciÃ³n de preferencias
        String sharedPrefFile = "com.uc3m.it.infocovid";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        SharedPreferences.Editor editor = mPreferences.edit();

        // Guardamos el valor de la preferencia
        editor.putString("zipCode", zipCode);
        editor.putString("comunidad", comunidad);
        editor.apply();

        Toast.makeText(BackgroundLocationService.this, "LAT: " + location.getLatitude() + "\n LONG: "
                + location.getLongitude() + " \n ZIPCODE: " + zipCode + "\n CCAA: " + comunidad, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "LAT: " + location.getLatitude() + "\n LONG: "
                + location.getLongitude() + " \n ZIPCODE: " + zipCode);
    }


}


