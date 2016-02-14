package com.adil.nytimes.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adil.nytimes.R;
import com.adil.nytimes.activities.ArticleActivity;
import com.adil.nytimes.models.Article;
import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adil on 2/11/16.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private static List<Article> listOfArticles;
    private Article article;


    public ArticlesAdapter(List<Article> articles){
        this.listOfArticles = articles;
    }

    public void addArticles(List<Article> articlesList){
        if (articlesList.size() > 0) {
            int curSize = this.getItemCount();
            listOfArticles.addAll(articlesList);
            notifyItemRangeInserted(curSize, this.getItemCount() - 1);
        }
    }

    public void clearData(){
        listOfArticles.clear();
        notifyDataSetChanged();
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
//            Picasso.with(holder.ivArticleThumbnail.getContext()).load(article.getWideThumbnailUrl()).into(holder.ivArticleThumbnail);
            Glide.with(holder.ivArticleThumbnail.getContext()).load(article.getWideThumbnailUrl()).into(holder.ivArticleThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return listOfArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        @Bind(R.id.tvArticleSnippet) TextView tvArticleSnippet;
        @Bind(R.id.ivArticleThumbnail) ImageView ivArticleThumbnail;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            Article article = listOfArticles.get(getLayoutPosition());
            Intent i = new Intent(v.getContext(), ArticleActivity.class);
            i.putExtra("article", Parcels.wrap(article));
            v.getContext().startActivity(i);
        }
    }

}
