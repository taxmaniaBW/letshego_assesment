package com.mokgethisi.letshegoassesment.repository;


import com.mokgethisi.letshegoassesment.data.mostviewedresponse.MostViewedResponse;
import com.mokgethisi.letshegoassesment.network.WebService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;

/**
 * Only purpose is to access data from local storage or remote
 */

public class NewsRepository {

    private final WebService apiService;

    @Inject
    public NewsRepository(WebService apiService) {
        this.apiService = apiService;
    }


    public Call<MostViewedResponse> getMostPopularNews(){
        return apiService.getPopularNews();
    }




}