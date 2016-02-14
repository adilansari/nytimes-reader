package com.adil.nytimes.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adil on 2/11/16.
 */
public class Article {

    private static final String TAG = Article.class.getSimpleName();

    private String snippet;
    private String sqThumbnailUrl;
    private String wideThumbnailUrl;
    private String webUrl;

    public String getSnippet() {
        return this.snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getWideThumbnailUrl() {
        return this.wideThumbnailUrl;
    }

    public void setWideThumbnailUrl(String wideThumbnailUrl) {
        this.wideThumbnailUrl = wideThumbnailUrl;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSqThumbnailUrl() {
        return this.sqThumbnailUrl;
    }

    public void setSqThumbnailUrl(String sqThumbnailUrl) {
        this.sqThumbnailUrl = sqThumbnailUrl;
    }

    public static Article fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null)
            return null;

        Article article = new Article();
        article.setWebUrl(jsonObject.getString("web_url"));
        article.setSnippet(jsonObject.getString("snippet"));

        JSONArray images = jsonObject.getJSONArray("multimedia");
        for (int i = 0; i < images.length(); i++){
            JSONObject img = images.getJSONObject(i);
            String imgUrl = "http://www.nytimes.com/" + img.getString("url");
            if (img.getString("subtype").equals("wide")){
                article.setWideThumbnailUrl(imgUrl);
            } else if (img.getString("subtype").equals("thumbnail")){
                article.setSqThumbnailUrl(imgUrl);
            }
        }
        return article;
    }

    public static List<Article> fromJson(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null)
            return null;

        List<Article> listOfArticles = new ArrayList<>();
        for (int i = 0; i<jsonArray.length(); i++) {
            listOfArticles.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return listOfArticles;
    }

}
