package com.flypika.mvvmproject

import android.util.Log
import com.flypika.mvvmproject.model.News
import com.flypika.mvvmproject.model.ResponseNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {

    private val service: WebService = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(WebService::class.java)

    companion object{

        private const val TAG = "DataRepository"
        private var instance: DataRepository? = null

        fun getInstance(): DataRepository{
            if (instance == null) {
                synchronized(this){
                    if (instance == null){
                        instance = DataRepository()
                    }
                }
            }
            return instance!!
        }
    }

    fun getNewsList(callback: (List<News>?) -> Unit){
        service.getTopHeadlines().enqueue(object : Callback<ResponseNews>{
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                if (response.isSuccessful){
                    Log.i(TAG, "Response is received: ${response.body()?.totalResults} news, status - ${response.body()?.status}")
                    if (response.body()!!.status == "ok")
                        callback(response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                Log.w(TAG, "Request is failed: ${t.localizedMessage}")
            }

        })
    }

}