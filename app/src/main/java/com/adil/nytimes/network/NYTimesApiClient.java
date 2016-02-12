package com.adil.nytimes.network;

import com.adil.nytimes.activities.SearchActivity;
import com.adil.nytimes.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adil on 2/11/16.
 */
public class NYTimesApiClient {

    private static final String TAG = NYTimesApiClient.class.getSimpleName();
    private static List<Article> listOfArticles;

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
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json?q="+query+"&document_type=article&sort=newest&api-key=c9e971792e54e3c1cb491cefce6014c9:1:74339277";
        return url;
    }
}
