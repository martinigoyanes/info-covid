package com.example.infocovid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>{

    ArrayList<Article>  articles;

    NewsAdapter(ArrayList<Article> articles){
        this.articles = articles;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView title;
        TextView summary;
        TextView link;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.article_card);
            title = (TextView)itemView.findViewById(R.id.article_title);
            summary = (TextView)itemView.findViewById(R.id.article_summary);
            link = (TextView)itemView.findViewById(R.id.article_link);
        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_item, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder articleViewHolder, int position) {
        Article article = this.articles.get(position);

        articleViewHolder.title.setText(article.getTitle());
        articleViewHolder.summary.setText(article.getSummary());
        articleViewHolder.link.setText(article.getLink());

        //articleViewHolder.itemView.setOnClickListener();
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

}