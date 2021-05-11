//==================================================================================================
//
//      Title:          NewsAdapter (InfoCovid)
//      Authors:        Martín Iglesias Goyanes
//                      Jacobo Del Castillo Monche
//                      Carlos García Guzman
//      Date:           12 May 2021
//
//==================================================================================================

package com.example.infocovid;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
        TextView date;
        TextView source;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.article_card);
            title = (TextView)itemView.findViewById(R.id.article_title);
            summary = (TextView)itemView.findViewById(R.id.article_summary);
            source = (TextView)itemView.findViewById(R.id.article_source);
            date = (TextView)itemView.findViewById(R.id.article_timeAndDate);

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
        articleViewHolder.source.setText(article.getSource());
        articleViewHolder.date.setText(article.getDate());


        articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = articles.get(position).getLink();
                Log.d("articleViewHolder", "Following link " + link);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

}