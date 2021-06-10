package com.mokgethisi.letshegoassesment.network;

import com.mokgethisi.letshegoassesment.data.mostviewedresponse.MostViewedResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;


public interface WebService {

    @GET("/svc/mostpopular/v2/viewed/7.json")
    Observable<MostViewedResponse> getPopularNews();
}