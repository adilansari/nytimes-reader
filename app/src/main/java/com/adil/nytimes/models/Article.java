package com.adil.nytimes.models;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adil on 2/11/16.
 */

@Parcel
public class Article implements Parcelable {

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

    public boolean hasImage(){
        return (this.getWideThumbnailUrl() != null);
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
            if(!img.has("url"))
                continue;

            String imgUrl = "http://www.nytimes.com/" + img.getString("url");

            if (img.has("subtype") && img.getString("subtype").equals("wide")){
                article.setWideThumbnailUrl(imgUrl);
            } else if (img.has("subtype") && img.getString("subtype").equals("thumbnail")){
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.snippet);
        dest.writeString(this.sqThumbnailUrl);
        dest.writeString(this.wideThumbnailUrl);
        dest.writeString(this.webUrl);
    }

    public Article() {
    }

    protected Article(android.os.Parcel in) {
        this.snippet = in.readString();
        this.sqThumbnailUrl = in.readString();
        this.wideThumbnailUrl = in.readString();
        this.webUrl = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(android.os.Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
