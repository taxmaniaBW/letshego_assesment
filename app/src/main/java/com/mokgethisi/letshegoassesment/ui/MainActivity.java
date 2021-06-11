package com.mokgethisi.letshegoassesment.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.mokgethisi.letshegoassesment.R;
import com.mokgethisi.letshegoassesment.adapter.NewsAdapter;
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result;
import com.mokgethisi.letshegoassesment.utils.Status;
import com.mokgethisi.letshegoassesment.viewmodel.NewsListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NewsListViewModel newsListViewModel;
    private static final String TAG = "Main Activity";
    private NewsAdapter adapter;
    private final List<Result> newsList = new ArrayList<>();
    private RecyclerView newsRecyclerView;
    private CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListViewModel = new ViewModelProvider(this).get(NewsListViewModel.class);
        newsRecyclerView = findViewById(R.id.recycler_view);
        circularProgressIndicator = findViewById(R.id.progress_bar);
        initRecyclerView();
        observeData();
        newsListViewModel.getNews();
    }

    private void observeData() {

        newsListViewModel.getNewsList().observe(this, resultState -> {
            if (resultState.getStatus().equals(Status.SUCCESS)){
                circularProgressIndicator.setVisibility(View.GONE);
                Log.e(TAG, "onChanged: " + Objects.requireNonNull(resultState.getData()).size() );
                adapter.updateList(resultState.getData());
            }
            else if (resultState.getStatus().equals(Status.ERROR)){
                Log.e(TAG, "observeData: "+resultState.getMessage() );
                circularProgressIndicator.setVisibility(View.GONE);
            }
            else {
                circularProgressIndicator.setVisibility(View.VISIBLE);
                Log.e(TAG, "observeData: LOADING" );
            }

        });
    }

    private void initRecyclerView() {
        adapter = new NewsAdapter(this,newsList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
       newsRecyclerView.setAdapter(adapter);

    }
}
