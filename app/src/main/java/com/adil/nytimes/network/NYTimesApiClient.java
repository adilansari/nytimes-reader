package com.adil.nytimes.network;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import com.adil.nytimes.activities.SearchActivity;
import com.adil.nytimes.models.Article;
import com.adil.nytimes.models.Settings;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adil on 2/11/16.
 */
public class NYTimesApiClient extends ContextWrapper{

    private static final String TAG = NYTimesApiClient.class.getSimpleName();
    private static List<Article> listOfArticles;
    private SharedPreferences prefs;

    public NYTimesApiClient(Context base) {
        super(base);
    }

    public void fetchArticles(String query){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(getUri(query), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    listOfArticles = Article.fromJson(response.getJSONObject("response").getJSONArray("docs"));
                    SearchActivity.articlesAdapter.addArticles(listOfArticles);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public String getUri(String query){
        Settings filter = Settings.getInstance(this);
        StringBuilder builder = new StringBuilder("http://api.nytimes.com/svc/search/v2/articlesearch.json?document_type=article&api-key=c9e971792e54e3c1cb491cefce6014c9:1:74339277");

        if(filter.getFilterEnabled()){
            StringBuilder fq = new StringBuilder("news_desk:(\"");
            fq.append(filter.getNewsDesk());
            fq.append("\")");
            builder.append("&fq=news_desk:(\"").append(filter.getNewsDesk()).append("\")");
            builder.append("&sort=").append(filter.getSortOrder().toLowerCase());
            builder.append("&begin_date=").append(filter.getBeginDate());
        }
        builder.append("&q=").append(query);

        String url = builder.toString();
        Log.d("URL", url);
        return url;
    }
}
