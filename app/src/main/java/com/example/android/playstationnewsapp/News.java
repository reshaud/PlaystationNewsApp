package com.example.android.playstationnewsapp;

import java.util.Date;

/**
 * Created by Reshaud Ally on 4/19/2017.
 */

public class News {
    private String mWebTitle;
    private String mSectionName;
    private String mPublishedDate;
    private String mwebURL;

    public News(String webTitle, String sectionName, String  publishedDate,String webURL) {
        this.mWebTitle = webTitle;
        this.mSectionName = sectionName;
        this.mPublishedDate = publishedDate;
        this.mwebURL = webURL;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public String getMwebURL() {
        return mwebURL;
    }
}
