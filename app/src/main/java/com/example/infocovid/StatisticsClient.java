//==================================================================================================
//
//      Title:          StatisticsClient (InfoCovid)
//      Authors:        Martín Iglesias Goyanes
//                      Jacobo Del Castillo Monche
//                      Carlos García Guzman
//      Date:           12 May 2021
//
//==================================================================================================

package com.example.infocovid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.MODE_PRIVATE;

public class StatisticsClient extends AsyncTask<View, Void, HashMap<String, String>> {
    Context context;

    public StatisticsClient(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected HashMap<String, String> doInBackground(View... views) {
        HashMap<String, String> result;
        Date currDate = null;
        Date prevDate = null;

        boolean sameDay = false;
        boolean sameRegion = false;

        // Cargamos region y fecha del disk
        String sharedPrefFile = "com.uc3m.it.infocovid";
        SharedPreferences mPreferences = this.context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String prevDateString = mPreferences.getString("date", "2020-01-01");


        //If we have last_updated = today, we dont need to update untill tomorrow
        LocalDateTime timeNow = LocalDateTime.now();
        String nowString = timeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            currDate = simpleDateFormat.parse(nowString);
            prevDate = simpleDateFormat.parse(prevDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(currDate);

        // We increment by one the day of the prevDate since stats are always shown on the day x+1
        cal2.setTime(prevDate);
        cal2.add(Calendar.DATE, 1);

        // If currentDay is  equal or older than prevDay, then we abort, we already have latest stats
        //if (cal1.get(Calendar.DAY_OF_YEAR) <= cal2.get(Calendar.DAY_OF_YEAR))
            //sameDay = true;
        //else
            prevDateString = nowString;


        String region = mPreferences.getString("comunidad", "null");


        String endPoint = "https://api.covid19tracking.narrativa.com/api/" + prevDateString + "/country/spain/region/" + region;

        Log.d("StatsClient", endPoint);

        result = makeCall(endPoint, prevDateString);

        return result;

    }

    @Override
    protected void onPostExecute(HashMap<String, String> statistics) {
        // Save stats
        // Creamos colecciÃ³n de preferencias
        String sharedPrefFile = "com.uc3m.it.infocovid";
        SharedPreferences mPreferences = this.context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        for (String stat : statistics.keySet()) {
            // Guardamos el valor de la estadistica
            editor.putString(stat, statistics.get(stat));
            editor.apply();
        }
        
    }

    public static HashMap<String, String> makeCall(String stringURL, String date) {
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

        HashMap<String, String> statistics = new HashMap<String, String>();
        String key = "";

        try {
            jsonReader = new JsonReader(new InputStreamReader(buffer, "UTF-8"));

            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                key = jsonReader.nextName();
                if (key.equals("dates")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        key = jsonReader.nextName();
                        if (key.equals(date)) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                key = jsonReader.nextName();
                                if (key.equals("countries")) {
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()) {
                                        key = jsonReader.nextName();
                                        if (key.equals("Spain")) {
                                            jsonReader.beginObject();
                                            while (jsonReader.hasNext()) {
                                                key = jsonReader.nextName();
                                                if (key.equals("regions")) {
                                                    jsonReader.beginArray();
                                                    while (jsonReader.hasNext()) {
                                                        jsonReader.beginObject();
                                                        String value = "";
                                                        while (jsonReader.hasNext()) {
                                                            key = jsonReader.nextName();
                                                            if (key.equals("today_new_confirmed")) {
                                                                value = jsonReader.nextString();
                                                                statistics.put(key, value);
                                                            } else if (key.equals("today_new_deaths")) {
                                                                value = jsonReader.nextString();
                                                                statistics.put(key, value);
                                                            } else if (key.equals("date")) {
                                                                value = jsonReader.nextString();
                                                                statistics.put("last_updated", value);
                                                            } else {
                                                                jsonReader.skipValue(); // Skip values of other keys
                                                            }
                                                        }
                                                        jsonReader.endObject();
                                                    }
                                                    jsonReader.endArray();
                                                } else {
                                                    jsonReader.skipValue(); // Skip values of other keys
                                                }
                                            }
                                            jsonReader.endObject();
                                        }
                                    }
                                    jsonReader.endObject();
                                } else {
                                    jsonReader.skipValue(); // Skip values of other keys
                                }
                            }
                            jsonReader.endObject();
                        }
                    }
                    jsonReader.endObject();
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            jsonReader.endObject();

        } catch (Exception e) {
            e.printStackTrace();
            myConnection.disconnect();
            return statistics;
        }
        Log.d("StatsClient", "Obtained " + statistics.toString());
        return statistics;
    }
}


