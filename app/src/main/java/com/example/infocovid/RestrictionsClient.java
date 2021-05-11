//==================================================================================================
//
//      Title:          RestrictionsClient (InfoCovid)
//      Authors:        Martín Iglesias Goyanes
//                      Jacobo Del Castillo Monche
//                      Carlos García Guzman
//      Date:           12 May 2021
//
//==================================================================================================

package com.example.infocovid;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import static android.content.Context.MODE_PRIVATE;

public class RestrictionsClient extends AsyncTask<View,Void, HashMap<String, String>> {
    ExpandableListView restrictionsView;
    Context context;
    String zipCode = "";
    String province = "";
    boolean display;

    public RestrictionsClient(ExpandableListView restrictionsView, Context context, String zipCode, String province) {
        this.restrictionsView = restrictionsView;
        this.context = context;
        this.zipCode = zipCode;
        this.province = province;
        this.display = true;
    }

    public RestrictionsClient(Context context, String zipCode, String province, boolean display) {
        this.restrictionsView = null;
        this.context = context;
        this.zipCode = zipCode;
        this.province = province;
        this.display = display;
    }

    @Override
    protected HashMap<String, String> doInBackground(View... views) {
        HashMap<String, String> result;
        String apiKey = "z01h3G9ueaAVelCMd0ytK0Net";
        String endPoint = "";

        // Si buscamos por zipcode o buscamos por provincia son dos endpoints diferentes
        if(this.zipCode != null)
            try {
                endPoint = "https://api.quecovid.es/restriction/restriction?zipcode=" + this.zipCode + "&token=" + apiKey;
            } catch (Exception ex) {

            }
        else if(this.province != null)
            endPoint = "https://api.quecovid.es/restriction/restriction?place=provincia+de+" + this.province + "&token=" + apiKey;

        Log.d("RestrictionsClient", endPoint);

        result = makeCall(endPoint);

        return result;

    }

    @Override
    protected void onPostExecute(HashMap<String, String> restrictions) {
        // Generate restrictions view
        if(this.display==true) {
            List<String> restrictionsTitles = new ArrayList<String>(restrictions.keySet());
            CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this.context, restrictionsTitles, restrictions);
            this.restrictionsView.setAdapter(expandableListAdapter);
        }else {

            String toqueDeQueda = restrictions.get("Restringida la movilidad nocturna");
            if(toqueDeQueda!=null) {
                newTDQAlarm(toqueDeQueda);
            }


        }
    }

    public static HashMap<String, String>  makeCall(String stringURL) {
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

        HashMap<String, String> restrictions = new HashMap<String, String>();
        int restrictionsLength = 0;

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
                            restrictionsLength++;
                            String title = ""; String description = "";
                            while (jsonReader.hasNext()) {
                                key = jsonReader.nextName();
                                if (key.equals("title")) {
                                    title = jsonReader.nextString();
                                }
                                if (key.equals("description")) {
                                    description = jsonReader.nextString();
                                    restrictions.put(title, description);
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
            return restrictions;
        }
        Log.d("RestrictionsClient", "Obtained " + restrictionsLength + " restrictions");
        return restrictions;
    }

    public void newTDQAlarm(String toqueDeQueda){
        //Creamos la alarma
        Log.i("Prueba toque", toqueDeQueda);
        int index = toqueDeQueda.lastIndexOf("entre las");
        if(index==-1)
            index = toqueDeQueda.lastIndexOf("desde las");

        String horaCompleta = toqueDeQueda.substring(index + 10, index + 15);
        Log.i("prueba toque", "La hora completa es: " + horaCompleta);
        int hora = Integer.parseInt(horaCompleta.substring(0,2));
        int minutos = Integer.parseInt(horaCompleta.substring(3,5));
        minutos = minutos - 10;
        if(minutos<0) {
            hora = hora-1;
            minutos = 60 + minutos;
        }
        if(hora<0) {
            hora = 24 + hora;
        }
        Log.i("prueba toque", "La hora de la alarma es: " + hora + ":" + minutos);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);

        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                alarmPendingIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // crea canal de notificaciones
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext(), "com.uc3m.it.infocovid.notify_001");
        SharedPreferences mPreferences = context.getSharedPreferences("com.uc3m.it.infocovid", MODE_PRIVATE);
        String comunidad = mPreferences.getString("comunidad", null);

        Intent intent2 = new Intent(context.getApplicationContext(), DisplayRestrictionsActivity.class);
        intent2.putExtra("province", comunidad);
        intent2.putExtra("activity", "MainActivity");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_covid_notification);
        mBuilder.setContentTitle("Alarma activada");
        mBuilder.setContentText("Alarma de toque de queda para las: " + hora + ":" + minutos);
        mBuilder.setChannelId("checkRestrictions");

        notificationManager.notify(0, mBuilder.build());
    }
}

