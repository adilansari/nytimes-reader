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
public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static List<Article> listOfArticles;
    private Article article;
    private final int TEXT = 0, IMAGE = 1;

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
    public int getItemViewType(int position) {
        if (listOfArticles.get(position).hasImage())
            return IMAGE;

        return TEXT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        View articleView;

        switch (viewType){
            case IMAGE:
                articleView = layoutInflater.inflate(R.layout.item_image_grid_article, parent, false);
                viewHolder = new ImgArticleViewHolder(articleView);
                break;
            default:
                articleView = layoutInflater.inflate(R.layout.item_text_grid_layout, parent, false);
                viewHolder = new TextArticleViewHolder(articleView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        article = listOfArticles.get(position);

        switch (viewHolder.getItemViewType()){
            case IMAGE:
                ImgArticleViewHolder imgViewHolder = (ImgArticleViewHolder) viewHolder;
                imgViewHolder.tvImageArticleSnippet.setText(article.getSnippet());
                imgViewHolder.ivArticleThumbnail.setImageResource(0);
                Glide.with(imgViewHolder.ivArticleThumbnail.getContext()).load(article.getWideThumbnailUrl()).into(imgViewHolder.ivArticleThumbnail);
                break;
            default:
                TextArticleViewHolder textViewHolder = (TextArticleViewHolder) viewHolder;
                textViewHolder.tvTextArticleSnippet.setText(article.getSnippet());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listOfArticles.size();
    }

    public static abstract class ArticleViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        public ArticleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Article article = listOfArticles.get(getLayoutPosition());
            Intent i = new Intent(v.getContext(), ArticleActivity.class);
            i.putExtra("article", Parcels.wrap(article));
            v.getContext().startActivity(i);
        }
    }

    public static class ImgArticleViewHolder extends ArticleViewHolder{

        @Bind(R.id.tvImageArticleSnippet) TextView tvImageArticleSnippet;
        @Bind(R.id.ivArticleThumbnail) ImageView ivArticleThumbnail;

        public ImgArticleViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TextArticleViewHolder extends ArticleViewHolder{

        @Bind(R.id.tvTextArticleSnippet) TextView tvTextArticleSnippet;

        public TextArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
