package com.example.infocovid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NewsClient extends AsyncTask<View,Void, ArrayList<Article>> {
    RecyclerView newsView;

    public NewsClient(RecyclerView newsView) {
        this.newsView = newsView;
    }

    @Override
    protected ArrayList<Article> doInBackground(View... views) {
        ArrayList<Article> result;
        String endPoint = "https://covid-19-news.p.rapidapi.com/v1/covid?q=covid&lang=es&sort_by=date&country=es&media=False";
        String apiKey = "29a3b88fc1msh14bcabee9028db5p11ffb1jsn77b76ed83b3d";
        String apiHost = "covid-19-news.p.rapidapi.com";

        Log.d("NewsClient", endPoint);

        result = makeCall(endPoint, apiKey, apiHost);

        return result;

    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        // Generate articles view
        NewsAdapter newsAdapter = new NewsAdapter(articles);
        this.newsView.setAdapter(newsAdapter);

    }

    public static ArrayList<Article> makeCall(String stringURL, String apiKey, String apiHost) {
        URL url = null;
        BufferedInputStream buffer = null;
        JsonReader jsonReader;
        HttpsURLConnection myConnection = null;
        ArrayList<Article> result = new ArrayList<Article>();

        // Create URL
        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            Log.e("NewsClient", "Malformed URL");
        }

        // Create connection
        try {
            if (url != null) {
                myConnection = (HttpsURLConnection) url.openConnection();
                myConnection.setRequestProperty("x-rapidapi-key", apiKey);
                myConnection.setRequestProperty("x-rapidapi-host", apiHost);
                int responseCode = myConnection.getResponseCode();
                if (responseCode == 200) {
                    buffer = new BufferedInputStream(myConnection.getInputStream());
                } else {
                    throw new IOException("Respone code: " + responseCode);
                }
            }
        } catch (IOException ioe) {
            Log.d("NewsClient", "Exception connecting to " + stringURL + "\n" + "Exception:\t" + ioe.toString());
        }

        // Start parsing JSON
        String link = "";
        String summary = "";
        String title = "";
        try {
            jsonReader = new JsonReader(new InputStreamReader(buffer, "UTF-8"));

            jsonReader.beginObject(); // Start processing the JSON object
            while (jsonReader.hasNext()) { // Loop through all keys
                String key = jsonReader.nextName(); // Fetch the next key
                if (key.equals("articles")) {
                    jsonReader.beginArray(); // Start processing reading article by article
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject(); // Open article object
                        while (jsonReader.hasNext()) { // Start reading each field inside an article
                            key = jsonReader.nextName();
                            if (key.equals("link")) { // Check if field is what we want
                                link = jsonReader.nextString(); // Read link
                            } else if (key.equals("summary")) {
                                summary = jsonReader.nextString();
                            } else if (key.equals("title")) {
                                title = jsonReader.nextString();
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                            // Create article
                            Article article = new Article(link, summary, title);
                            result.add(0, article);
                        }
                    }
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            // Close JSON and Connection
            jsonReader.close();
            myConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            myConnection.disconnect();
            return result;
        }
        Log.d("NewsClient", "Obtained " + result.size() + " news");
        return result;
    }
}