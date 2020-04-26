package com.chaacho.theguardiannewsapp.tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;
import android.util.Log;

import com.chaacho.theguardiannewsapp.pojo.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class RequestNews {
    private static final String LOG_TAG = RequestNews.class.getSimpleName();


    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // TODO ERASE THE LOG THIS
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<News> newsList = extractFeatureFromJSON(jsonResponse);

        return newsList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
//todo eliminate este log
            Log.e(LOG_TAG, "Problem building the URL.", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Tools.READ_TIMEOUT);
            urlConnection.setConnectTimeout(Tools.CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(Tools.REQUEST_METHOD_GET);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == Tools.SUCCESS_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                //todo eraase this log
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            //todo eraase this log

            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<News> extractFeatureFromJSON(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Tools.KEY_RESPONSE);
            JSONArray resultsArray = responseJsonObject.getJSONArray(Tools.KEY_RESULTS);
            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentNews = resultsArray.getJSONObject(i);
                String webTitle = currentNews.getString(Tools.KEY_WEB_TITLE);
                String sectionName = currentNews.getString(Tools.KEY_SECTION_NAME);
                String webPublicationDate = currentNews.getString(Tools.KEY_WEB_PUBLICATION_DATE);
                String webUrl = currentNews.getString(Tools.KEY_WEB_URL);
                String author = null;
                if (currentNews.has(Tools.KEY_TAGS)) {
                    JSONArray tagsArray = currentNews.getJSONArray(Tools.KEY_TAGS);
                    if (tagsArray.length() != 0) {
                        JSONObject firstTagsItem = tagsArray.getJSONObject(0);
                        author = firstTagsItem.getString(Tools.KEY_WEB_TITLE);
                    }
                }
                String thumbnail = null;
                String trailText = null;
                if (currentNews.has(Tools.KEY_FIELDS)) {
                    JSONObject fieldsObject = currentNews.getJSONObject(Tools.KEY_FIELDS);
                    if (fieldsObject.has(Tools.KEY_THUMBNAIL)) {
                        thumbnail = fieldsObject.getString(Tools.KEY_THUMBNAIL);
                    }
                    if (fieldsObject.has(Tools.KEY_TRAIL_TEXT)) {
                        trailText = fieldsObject.getString(Tools.KEY_TRAIL_TEXT);
                    }
                }
                News news = new News(webTitle, sectionName, author, webPublicationDate, webUrl, thumbnail, trailText);

                newsList.add(news);
            }
        } catch (JSONException e) {
//TODO BORRAR ESTE LOG
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }

        // Return the list of news
        return newsList;
    }
}

