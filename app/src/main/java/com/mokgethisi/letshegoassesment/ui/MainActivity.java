package com.mokgethisi.letshegoassesment.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.mokgethisi.letshegoassesment.R;
import com.mokgethisi.letshegoassesment.adapter.NewsAdapter;
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result;
import com.mokgethisi.letshegoassesment.viewmodel.NewsListViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NewsListViewModel newsListViewModel;
    private static String TAG = "Main Activity";
    private NewsAdapter adapter;
    private List<Result> newsList = new ArrayList<>();
    private RecyclerView newsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListViewModel = new ViewModelProvider(this).get(NewsListViewModel.class);
        newsRecyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
        observeData();
        newsListViewModel.getNews();
    }

    private void observeData() {

        newsListViewModel.getNewsList().observe(this, news -> {
           Log.e(TAG, "onChanged: " + news.size() );
            adapter.updateList(news);
        });
    }

    private void initRecyclerView() {
        adapter = new NewsAdapter(this,newsList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
       newsRecyclerView.setAdapter(adapter);

    }
}
