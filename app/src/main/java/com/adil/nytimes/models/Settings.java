package com.adil.nytimes.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.adil.nytimes.R;
import com.adil.nytimes.activities.SearchActivity;
import com.adil.nytimes.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by adil on 2/13/16.
 */
public class Settings {
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor edit;

    public static Settings getInstance(Context base){
        Settings instance = new Settings();
        prefs = PreferenceManager.getDefaultSharedPreferences(base);
        edit = prefs.edit();
        return instance;
    }

    public void setFilterEnabled(Boolean enabled){
        edit.putBoolean("filterEnabled", enabled);
        edit.apply();
        if (enabled)
            SearchActivity.filterButton.setIcon(R.drawable.ic_filter_orange_24);
        else
            SearchActivity.filterButton.setIcon(R.drawable.ic_filter_24);
    }

    public void setBeginDate(Date date){
        edit.putString("beginDate", DateUtil.getUrlFormattedDate(date));
        edit.apply();
    }

    public void setNewsDesk(NewsDesk nd){
        edit.putString("newsDesk", nd.toString());
        edit.apply();
    }

    public void setSortOrder(SortOrder so){
        edit.putString("sortOrder", so.toString());
        edit.apply();
    }

    public void setLastQuery(String query){
        edit.putString("query", query);
        edit.apply();
    }

    public boolean getFilterEnabled(){
        return prefs.getBoolean("filterEnabled", false);
    }

    public String getBeginDate(){
        return prefs.getString("beginDate", DateUtil.getUrlFormattedDate(new Date()));
    }

    public String getBeginDateForView(){
        Date d = new Date();
        try {
            d = DateUtil.getUrlFormatStringAsDate(getBeginDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateUtil.getDateAsString(d);
    }

    public String getNewsDesk(){
        return prefs.getString("newsDesk", NewsDesk.ARTS.toString());
    }

    public NewsDesk getNewsDeskForView(){
        return NewsDesk.get(getNewsDesk());
    }

    public String getSortOrder(){
        return prefs.getString("sortOrder", SortOrder.Old.toString());
    }

    public SortOrder getSortOrderForView(){
        return SortOrder.get(getSortOrder());
    }

    public String getLastQuery(){
        return prefs.getString("query", "");
    }
}

