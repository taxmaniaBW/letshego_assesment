package com.mokgethisi.letshegoassesment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.mokgethisi.letshegoassesment.MockResponseFileReader
import com.mokgethisi.letshegoassesment.data.mostviewedresponse.Result
import com.mokgethisi.letshegoassesment.network.HttpClientHelper
import com.mokgethisi.letshegoassesment.network.WebService
import com.mokgethisi.letshegoassesment.repository.NewsRepository
import com.mokgethisi.letshegoassesment.utils.ResultState
import com.mokgethisi.letshegoassesment.utils.Status
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import junit.framework.Assert.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


@RunWith(MockitoJUnitRunner::class)
class NewsListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsListViewModel

    private lateinit var repository: NewsRepository

    private lateinit var webService: WebService

    @Mock
    private lateinit var apiGetNewsObserver:  Observer<ResultState<List<Result>>>

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HttpClientHelper.getHttpClientHelper())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(WebService::class.java)

        repository = NewsRepository(retrofit);


        viewModel = NewsListViewModel(repository);
        viewModel.newsList.observeForever(apiGetNewsObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()

    }

    @Test
    fun `read sample success json file`(){
        val reader = MockResponseFileReader("api_success.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `check if successful data retrieval emits 200`(){
        // Assign
        val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockResponseFileReader("api_success.json").content)
        mockWebServer.enqueue(response)
        // Act
        val  actualResponse = repository.mostPopularNews.execute()
        // Assert

        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
        // Act

    }
    @Test
    fun `check if api response is not null`(){
        // Assign

        // Act
        val  actualResponse = repository.mostPopularNews.execute()


        Truth.assertThat(actualResponse.body()).isNotNull()

    }

}