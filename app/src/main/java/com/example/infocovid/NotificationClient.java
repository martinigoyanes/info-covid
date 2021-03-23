package com.example.infocovid;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.MODE_PRIVATE;

public class NotificationClient extends AsyncTask<View,Void, String> {
    Context context;
    public NotificationClient(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(View... views) {

        // Load current zipCode from disk
        String sharedPrefFile = "com.uc3m.it.infocovid";
        SharedPreferences mPreferences = this.context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String zipCode = mPreferences.getString("zipCode", "0");
        Log.d("NoticiationClient","checkCurfew(): ZIPCODE: " + zipCode);

        String apiKey = "z01h3G9ueaAVelCMd0ytK0Net";
        String endPoint = "https://api.quecovid.es/restriction/restriction?zipcode=" + zipCode + "&token=" + apiKey;

        Log.d("NotificationClient", endPoint);

        return makeCall(endPoint);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPostExecute(String curfewRestriction) {

        // Check if curfew is 30 min close and only notify in 5 min intervals, if so we send a notification
        long differenceInMinutes = checkCurfew(curfewRestriction);
        if(differenceInMinutes%5 == 0 && differenceInMinutes > 0 && differenceInMinutes <30)
            notifyCurfew(differenceInMinutes);
    }

    public static String makeCall(String stringURL) {
        URL url = null;
        BufferedInputStream buffer = null;
        JsonReader jsonReader;
        HttpsURLConnection myConnection = null;

        // Create URL
        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            Log.e("RestrictionsClient", "Malformed URL");
        }

        // Create connection
        try {
            if (url != null) {
                myConnection = (HttpsURLConnection) url.openConnection();
                int responseCode = myConnection.getResponseCode();
                if (responseCode == 200) {
                    buffer = new BufferedInputStream(myConnection.getInputStream());
                } else {
                    throw new IOException("Respone code: " + responseCode);
                }
            }
        } catch (IOException ioe) {
            Log.d("RestrictionsClient", "Exception connecting to " + stringURL + "\n" + "Exception:\t" + ioe.toString());
        }

        String curfewRestriction = "";

        try {
            jsonReader = new JsonReader(new InputStreamReader(buffer, "UTF-8"));
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String key = jsonReader.nextName();
                    if (key.equals("restrictions")) {
                        jsonReader.beginArray(); // Start reading each object of restrictions <title, desc>
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            String title = ""; String description = "";
                            while (jsonReader.hasNext()) {
                                key = jsonReader.nextName();
                                if (key.equals("title")) {
                                    title = jsonReader.nextString();
                                }
                                if (key.equals("description")) {
                                    description = jsonReader.nextString();
                                    if(title.equals("Restringida la movilidad nocturna")){
                                        curfewRestriction = description;
                                        Log.d("NotificationClient", curfewRestriction);
                                        return curfewRestriction;
                                    }
                                }
                            }
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                    }else {
                        jsonReader.skipValue(); // Skip values of other keys
                    }
                }
                jsonReader.endObject();
            }
            jsonReader.endArray();
        }catch (Exception e) {
            e.printStackTrace();
            myConnection.disconnect();
            return curfewRestriction;
        }
        Log.d("NotificationClient", curfewRestriction);
        return curfewRestriction;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long checkCurfew(String curfewRestriction){

        // Get the restriction for the curfew
        String curfewString = curfewRestriction.substring(curfewRestriction.indexOf(":")-2,curfewRestriction.indexOf(":")+3);
        curfewString = curfewString+":00";

        //Get current time
        LocalDateTime timeNow = LocalDateTime.now();
        String nowString = timeNow.format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH));

        // Parse strings to date objects
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date curfewDate = null;
        Date nowDate = null;

        // Trick to see if it works fixing curfew to now+1min
        // curfewString = "12:00:00";
        try {
            curfewDate = simpleDateFormat.parse(curfewString);
            if(curfewString.equals("00:00:00")){
                // If its midnight we need to make it midnight of the following day
                Calendar c = Calendar.getInstance();
                c.setTime(curfewDate);
                c.add(Calendar.DATE, 1);  // number of days to add
                curfewDate = c.getTime();
            }

            nowDate = simpleDateFormat.parse(nowString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Calculate time till curfew
        // Calculating the difference in Minutes
        long differenceInMinutes = ((curfewDate.getTime() - nowDate.getTime()) / (60 * 1000));
        Log.d("NoticiationClient", differenceInMinutes + " min until curfew ("+curfewString+")");

        return differenceInMinutes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notifyCurfew(long differenceInMinutes) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.context)
                        .setSmallIcon(R.drawable.logo) //set icon for notification
                        .setContentTitle("Toque de queda cerca")
                        .setContentText(differenceInMinutes + " minutos para el toque de queda en tu zona!")
                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        // Add as notification
        NotificationManager manager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Curfew";
        NotificationChannel channel = new NotificationChannel(
                channelId,
                "curfewChannel",
                NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        builder.setChannelId(channelId);
        manager.notify(0, builder.build());
    }

}


