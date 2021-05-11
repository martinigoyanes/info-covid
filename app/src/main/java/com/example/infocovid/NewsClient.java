//==================================================================================================
//
//      Title:          NewsClient (InfoCovid)
//      Authors:        Martín Iglesias Goyanes
//                      Jacobo Del Castillo Monche
//                      Carlos García Guzman
//      Date:           12 May 2021
//
//==================================================================================================

package com.example.infocovid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

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
        String date = "";
        String source = "";
        int maxSummaryLength = 300;

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
                            if (key.equals("summary")) { // Check if field is what we want
                                summary = jsonReader.nextString(); // Read summary
                                if(summary.length() > maxSummaryLength){
                                    //Keep only first 300 chars and put "..." to indicate there is more
                                    summary = summary.substring(0, Math.min(summary.length(), maxSummaryLength)); // keep only first 300 chars
                                    summary = summary + "...";
                                }

                            } else if (key.equals("clean_url")) {
                                source = jsonReader.nextString();
                            } else if (key.equals("link")) {
                                link = jsonReader.nextString();
                            }else if (key.equals("title")) {
                                title = jsonReader.nextString();
                            }else if (key.equals("published_date")) {
                                date = jsonReader.nextString(); // date = "2021-03-19 09:13:41"
                                date = date.substring(5, date.length()); // remove year -> "03-19 09:13:41"
                                String[] timeAndDay = date.split(" "); // split by space -> "03-19", "09:13:41"
                                String[] monthAndDay = timeAndDay[0].split("-");  // Split month and day -> "03","19"
                                timeAndDay[0] = monthAndDay[1] + "-" + monthAndDay[0];   // Rearrange so it is day-month -> "19","03"
                                date = timeAndDay[0] + " " + timeAndDay[1]; // Put whole date together back
                                date = date.substring(0, date.length()-3);// Remove seconds, no one cares -> "19-03 09:13"
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.endObject();
                        // Create article
                        Article article = new Article(link, summary, title, date, source);
                        result.add(0, article);
                    }
                    jsonReader.endArray();
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }
            }
            jsonReader.endObject();
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

