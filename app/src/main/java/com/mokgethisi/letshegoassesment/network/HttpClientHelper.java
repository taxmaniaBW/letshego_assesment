package com.mokgethisi.letshegoassesment.network;


import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.mokgethisi.letshegoassesment.utils.Constants.API_KEY;

/**
 *
 * Helper class for intercepting network calls,
 * Main purpose is to add the api key to every network call
 * Assumption being api.nytimes.com is the only api service we will be calling
 */

public class HttpClientHelper {

    /**
     * Create okHttpClient and add an interceptor to intercept all calls made
     * @return okHttpClient
     */
    public static OkHttpClient getHttpClientHelper() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(10, TimeUnit.SECONDS)

                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        client.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api-key", API_KEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);

        });


        return client.build();
    }
}
