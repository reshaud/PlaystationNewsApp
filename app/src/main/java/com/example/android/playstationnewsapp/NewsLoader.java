package com.example.android.playstationnewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Reshaud Ally on 4/26/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<News> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null
        if (url == null) {
            return null;
        }

        List<News> results = QueryUtils.fetchNewsData(url);
        return results;
    }

    //Start loading data
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
