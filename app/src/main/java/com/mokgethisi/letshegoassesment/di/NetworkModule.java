package com.mokgethisi.letshegoassesment.di;

import com.mokgethisi.letshegoassesment.network.HttpClientHelper;
import com.mokgethisi.letshegoassesment.network.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public static WebService provideWebService(){

        return  new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientHelper.getHttpClientHelper())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(WebService.class);
    }
}