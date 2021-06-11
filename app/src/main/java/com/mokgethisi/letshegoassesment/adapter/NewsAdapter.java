package com.mokgethisi.letshegoassesment.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mokgethisi.letshegoassesment.R;
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result;
import com.mokgethisi.letshegoassesment.ui.DetailsActivity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context mContext;
    private List<Result> newsList;
    private static  String TAG = "NEWS ADAPTER";


    public NewsAdapter(Context mContext, List<Result> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
      Result result = newsList.get(position);
      holder.title.setText(result.getTitle());
      holder.date.setText(result.getPublishedDate());
     holder.author.setText(result.getByline());
     if (result.getMedia().size() != 0){
         Glide.with(mContext).load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).circleCrop().into(holder.thumbNail);
     }

     holder.itemView.setOnClickListener(view -> {
         Intent intent = new Intent(mContext, DetailsActivity.class);
         intent.putExtra("newsItem",result);
         mContext.startActivity(intent);
     });


    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbNail;
        TextView title,author,date;


        NewsViewHolder(View itemView) {
            super(itemView);
            thumbNail = itemView.findViewById(R.id.news_item_thumbnail);
            title = itemView.findViewById(R.id.news_item_headline);
            author = itemView.findViewById(R.id.news_item_author);
            date = itemView.findViewById(R.id.news_item_date);

        }
    }

    public  void updateList(List<Result> updatedList){
        newsList = updatedList;
        notifyDataSetChanged();
    }


}