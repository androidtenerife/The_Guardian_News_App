package com.chaacho.theguardiannewsapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaacho.theguardiannewsapp.R;
import com.chaacho.theguardiannewsapp.pojo.News;
import com.chaacho.theguardiannewsapp.tools.LoaderNews;
import com.chaacho.theguardiannewsapp.tools.SharedPreferences;

import java.util.List;



public class CultureFragment extends ArticleFragment {
    private static final String LOG_TAG = CultureFragment.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String worldUrl = SharedPreferences.getPreferredUrl(getContext(), getString(R.string.culture).toLowerCase());
        Log.e(LOG_TAG, worldUrl);

        // Create a new loader for the given URL
        return new LoaderNews(getActivity(), worldUrl);
    }
}