package com.example.android.playstationnewsapp;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<News>> {

    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?tag=technology/playstation&order-by=newest&api-key=test";

    private static final int NEWS_LOADER_ID = 1;

    private NewsAdapter newsAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //find a reference to listview in activity_main layout
        ListView newsListView = (ListView) findViewById(R.id.news_list_view);

        emptyView = (TextView) findViewById(R.id.empty_list);

        //create an adapter that takes in a empty list of news objects
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        //Set the view to be displayed if newsListView is empty
        newsListView.setEmptyView(emptyView);

        //Set the adapter on the newsListView
        newsListView.setAdapter(newsAdapter);

        //Set on item click listener on  the list view which sends an intent to the web browser
        //to open website with more information on a news item
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Find news object that was clicked on
                News currentNewsObject = newsAdapter.getItem(position);

                //Convert string into a Uri object
                Uri newsURI = null;
                if (currentNewsObject != null) {
                    newsURI = Uri.parse(currentNewsObject.getMwebURL());
                }

                //Create a new intent to view the newsURI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsURI);

                //send intent to launch activity
                startActivity(websiteIntent);
            }
        });

        if (!hasNetworkConnectivity(this)) {
            //Not Connected to internet
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        } else {
            //Connected to internet
            //Create a new loader if one exists or replace previous loader
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        // Clear the adapter
        newsAdapter.clear();

        //Set progress bar to GONE after load has finished
        progressBar.setVisibility(View.GONE);

        // If there is a valid list then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsList != null && !newsList.isEmpty()) {
            newsAdapter.addAll(newsList);
        }

        emptyView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu with refresh button
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            newsAdapter.clear();

            progressBar.setVisibility(View.VISIBLE);

            emptyView.setText(null);

            //Restart loader only if connected to internet
            if(hasNetworkConnectivity(this)){
                getLoaderManager().restartLoader(NEWS_LOADER_ID,null,this);
            }else {
                progressBar.setVisibility(View.GONE);
                emptyView.setText(R.string.no_internet);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Checks Network Connectivity
    public boolean hasNetworkConnectivity(Context context) {
        //Init connectivity manager which is used to check if we have internet access
        ConnectivityManager check = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get network information
        NetworkInfo networkInfo = check.getActiveNetworkInfo();

        return networkInfo != null;
    }
}
