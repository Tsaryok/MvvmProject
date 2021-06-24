package com.flypika.mvvmproject

import android.util.Log
import com.flypika.mvvmproject.model.News
import com.flypika.mvvmproject.model.ResponseNews
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {

    companion object {
        private const val TAG = "DataRepository"
        private var instance: DataRepository? = null

        fun getInstance(): DataRepository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = DataRepository()
                    }
                }
            }
            return instance!!
        }
    }

    private val service: WebService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        service = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(WebService::class.java)
    }

    fun getNewsList(callback: (ArrayList<News>?) -> Unit) {
        service.getTopHeadlines().enqueue(object : Callback<ResponseNews> {
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "Response is received: ${response.body()?.totalResults} news, status - ${response.body()?.status}, code - ${response.code()}")
                    if (response.body()!!.status == "ok") {
                        callback(response.body()?.articles)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Log.w(TAG, "Request is failed: ${t.localizedMessage}")
            }
        })
    }
}