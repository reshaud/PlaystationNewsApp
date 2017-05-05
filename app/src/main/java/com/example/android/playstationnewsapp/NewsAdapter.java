package com.example.android.playstationnewsapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.filterTouchesWhenObscured;
import static android.R.attr.resource;
import static com.example.android.playstationnewsapp.R.id.publishedDate;

/**
 * Created by Reshaud Ally on 4/22/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        //find current News object at a given position in list
        News currentNews = getItem(position);

        //Find TextView with id  webTitle
        TextView webTitle = (TextView) listItemView.findViewById(R.id.webTitle);

        //Find TextView with id section
        TextView sectionName = (TextView) listItemView.findViewById(R.id.section);

        //Find TextView with id publishedDate
        TextView publishedDate = (TextView) listItemView.findViewById(R.id.publishedDate);

        //Display WebTitle
        webTitle.setText(currentNews.getmWebTitle());

        //Display section name
        sectionName.setText(currentNews.getmSectionName());

        publishedDate.setText(formatDate(currentNews.getmPublishedDate()));

        return listItemView;
    }

    private String formatDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        String date = simpleDateFormat.format(dateObject);

        return date;
    }
}
