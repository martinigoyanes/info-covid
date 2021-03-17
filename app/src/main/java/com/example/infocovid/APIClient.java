package com.example.infocovid;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class APIClient extends AsyncTask<View,Void,Object> {


    @Override
    protected ArrayList<String> doInBackground(View... views) {
        ArrayList<String> result;
        String endPoint = "https://covid-19-news.p.rapidapi.com/v1/covid?q=covid&lang=es&sort_by=date&country=es&media=False";

        String apiKey = "29a3b88fc1msh14bcabee9028db5p11ffb1jsn77b76ed83b3d";
        String apiHost = "covid-19-news.p.rapidapi.com";

        Log.d("APIClient", endPoint);

        result = makeCall(endPoint, apiKey, apiHost);

        return result;

    }

    public static ArrayList<String> makeCall(String stringURL, String apiKey, String apiHost) {
        URL url = null;
        BufferedInputStream buffer = null;
        JsonReader jsonReader;
        HttpsURLConnection myConnection = null;
        ArrayList<String> result = new ArrayList<String>();

        // Create URL
        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            Log.e("APIClient","Malformed URL");
        }

        // Create connection
        try {
            if (url != null) {
                myConnection = (HttpsURLConnection) url.openConnection();
                myConnection.setRequestProperty("x-rapidapi-key", apiKey);
                myConnection.setRequestProperty("x-rapidapi-host", apiHost);
                int responseCode = myConnection.getResponseCode();
                if ( responseCode == 200) {
                    buffer = new BufferedInputStream(myConnection.getInputStream());
                } else {
                    throw new IOException("Respone code: " + responseCode);
                }
            }
        } catch (IOException ioe) {
            Log.d("APIClient", "Exception connecting to " + stringURL + "\n" + "Exception:\t" + ioe.toString());
        }

        // Start parsing JSON
        try {
            jsonReader = new JsonReader(new InputStreamReader(buffer, "UTF-8"));

            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String key = jsonReader.nextName(); // Fetch the next key
                if (key.equals("articles")) {
                    jsonReader.beginArray(); // Start processing the JSON object
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("link")) { // Check if desired key
                                // Fetch the value as a String
                                String value = jsonReader.nextString();
                                System.out.println(key + ": " + value);
                                result.add(key + ": " + value);
                            } else if (key.equals("summary")) {
                                String value = jsonReader.nextString();
                                System.out.println(key + ": " + value);
                                result.add(key + ": " + value);
                            } else if (key.equals("title")) {
                                String value = jsonReader.nextString();
                                System.out.println(key + ": " + value);
                                result.add(key + ": " + value);
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                    }
                }else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Close JSON and Connection
            jsonReader.close();
            myConnection.disconnect();
        }catch( Exception e){
            e.printStackTrace();
            myConnection.disconnect();
            return result;
        }

        return result;
    }
}
