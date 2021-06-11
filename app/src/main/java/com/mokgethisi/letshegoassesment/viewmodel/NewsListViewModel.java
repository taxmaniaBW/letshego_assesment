package com.mokgethisi.letshegoassesment.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mokgethisi.letshegoassesment.data.mostviewedresponse.MostViewedResponse;
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result;
import com.mokgethisi.letshegoassesment.repository.NewsRepository;
import com.mokgethisi.letshegoassesment.utils.ResultState;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class NewsListViewModel extends ViewModel {

    private static String  TAG = "NEWS_LIST_VIEW_MODEL";

    private final NewsRepository repository;
    private final MutableLiveData<ResultState<List<Result>>> newsList = new MutableLiveData<>();

    @Inject
    public NewsListViewModel(NewsRepository repository) {
        this.repository = repository;

    }



    public MutableLiveData<ResultState<List<Result>>> getNewsList() {
        return newsList;
    }

    public void getNews(){
        ResultState.Companion.loading(null);
        repository.getMostPopularNews().enqueue(new Callback<MostViewedResponse>() {
            @Override
            public void onResponse(@NotNull Call<MostViewedResponse> call, @NotNull Response<MostViewedResponse> response) {
                if(response.isSuccessful()){
                    MostViewedResponse mostViewedResponse = response.body();
                    if (mostViewedResponse != null)
                    newsList.postValue(ResultState.Companion.success(mostViewedResponse.getResults()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<MostViewedResponse> call, @NotNull Throwable t) {
                    newsList.postValue(ResultState.Companion.error(Objects.requireNonNull(t.getMessage()),null));
            }
        });

    }
}
