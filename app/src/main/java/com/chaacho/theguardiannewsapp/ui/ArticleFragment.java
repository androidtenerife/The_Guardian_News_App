package com.chaacho.theguardiannewsapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chaacho.theguardiannewsapp.R;
import com.chaacho.theguardiannewsapp.adapter.NewsAdapter;
import com.chaacho.theguardiannewsapp.pojo.News;
import com.chaacho.theguardiannewsapp.tools.EmptyRecyclerView;
import com.chaacho.theguardiannewsapp.tools.LoaderNews;
import com.chaacho.theguardiannewsapp.tools.SharedPreferences;
import com.chaacho.theguardiannewsapp.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = ArticleFragment.class.getName();
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        EmptyRecyclerView mRecyclerView = rootView.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                initiateRefresh();
                Toast.makeText(getActivity(), getString(R.string.updated_just_now),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        mRecyclerView.setEmptyView(mEmptyStateTextView);
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        mRecyclerView.setAdapter(mAdapter);
        initializeLoader(isConnected());

        return rootView;
    }
    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        Uri.Builder uriBuilder = SharedPreferences.getPreferredUri(getContext());
        Log.e(LOG_TAG,uriBuilder.toString());

        return new LoaderNews(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsData) {
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_news);
        mAdapter.clearAll();
        if (newsData != null && !newsData.isEmpty()) {
           mAdapter.addAll(newsData);
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mAdapter.clearAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader(isConnected());
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    private void initializeLoader(boolean isConnected) {
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.verify_conn);
            mEmptyStateTextView.setCompoundDrawablesWithIntrinsicBounds(Tools.DEFAULT_NUMBER,
                    R.drawable.logo,Tools.DEFAULT_NUMBER,Tools.DEFAULT_NUMBER);
        }
    }

    private void restartLoader(boolean isConnected) {
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.verify_conn);
            mEmptyStateTextView.setCompoundDrawablesWithIntrinsicBounds(Tools.DEFAULT_NUMBER,
                    R.drawable.logo,Tools.DEFAULT_NUMBER,Tools.DEFAULT_NUMBER);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    private void initiateRefresh() {
        restartLoader(isConnected());
    }
}
