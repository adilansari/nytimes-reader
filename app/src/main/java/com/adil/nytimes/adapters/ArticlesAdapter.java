package com.adil.nytimes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adil.nytimes.R;
import com.adil.nytimes.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adil on 2/11/16.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> listOfArticles;
    private Article article;


    public ArticlesAdapter(List<Article> articles){
        this.listOfArticles = articles;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View articleView = layoutInflater.inflate(R.layout.item_grid_article, parent, false);
        ViewHolder holder = new ViewHolder(articleView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        article = listOfArticles.get(position);
        holder.tvArticleSnippet.setText(article.getSnippet());
        holder.ivArticleThumbnail.setImageResource(0);
        if (article.getSqThumbnailUrl() != null){
            Picasso.with(holder.ivArticleThumbnail.getContext()).load(article.getWideThumbnailUrl()).into(holder.ivArticleThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return listOfArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tvArticleSnippet) TextView tvArticleSnippet;
        @Bind(R.id.ivArticleThumbnail) ImageView ivArticleThumbnail;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addArticles(List<Article> articlesList){
        int curSize = this.getItemCount();
        listOfArticles.addAll(articlesList);
        this.notifyItemRangeInserted(curSize, this.getItemCount()-1);
    }
}
