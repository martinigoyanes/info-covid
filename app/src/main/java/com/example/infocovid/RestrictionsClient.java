package com.example.infocovid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

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
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class RestrictionsClient extends AsyncTask<View,Void, HashMap<String, String>> {
    ExpandableListView restrictionsView;
    Context context;
    String zipCode = "";
    String province = "";

    public RestrictionsClient(ExpandableListView restrictionsView, Context context, String zipCode, String province) {
        this.restrictionsView = restrictionsView;
        this.context = context;
        this.zipCode = zipCode;
        this.province = province;
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
        List<String> restrictionsTitles = new ArrayList<String>(restrictions.keySet());
        CustomExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this.context, restrictionsTitles, restrictions);
        this.restrictionsView.setAdapter(expandableListAdapter);
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
}

