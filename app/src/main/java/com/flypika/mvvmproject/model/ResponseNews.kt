package com.flypika.mvvmproject.model

data class ResponseNews(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
)
