package com.mokgethisi.letshegoassesment.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mokgethisi.letshegoassesment.data.mostviewedresponse.MostViewedResponse;
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result;
import com.mokgethisi.letshegoassesment.repository.NewsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NewsListViewModel extends ViewModel {

    private static String  TAG = "NEWS_LIST_VIEW_MODEL";

    private NewsRepository repository;
    private MutableLiveData<List<Result>> newsList = new MutableLiveData<>();

    @Inject
    public NewsListViewModel(NewsRepository repository) {
        this.repository = repository;

    }
    public MutableLiveData<List<Result>> getNewsList() {
        return newsList;
    }

    public void getNews(){
        repository.getMostPopularNews()
                .subscribeOn(Schedulers.io())
                .map(MostViewedResponse::getResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> newsList.setValue(result),
                        error-> Log.e(TAG, "getNews: " + error.getMessage() ));
    }
}
