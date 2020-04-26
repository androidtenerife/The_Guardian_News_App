package com.chaacho.theguardiannewsapp.tools;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.chaacho.theguardiannewsapp.pojo.News;

import java.util.List;

/**
 * Loads a list of news by using an AsyncTask to perform the network request to the given URL.
 */
public class LoaderNews extends AsyncTaskLoader<List<News>> {

    /** Tag for log messages */
    private static final String LOG_TAG = LoaderNews.class.getName();

    /** Query URL */
    private String mUrl;


    public LoaderNews(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        // Trigger the loadInBackground() method to execute.
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<News> newsData = RequestNews.fetchNewsData(mUrl);
        return newsData;
    }
}
