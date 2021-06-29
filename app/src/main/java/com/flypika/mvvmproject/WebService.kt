package com.flypika.mvvmproject

import com.flypika.mvvmproject.model.ResponseNews
import io.reactivex.Observable
import retrofit2.http.GET

interface WebService {
    @GET("top-headlines?country=us&apiKey=5ce08297f18f406bb4373cbc0d7a7627")
    fun getTopHeadlines(): Observable<ResponseNews>
}