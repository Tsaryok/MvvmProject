package com.flypika.mvvmproject.model

// тоже желательно поля с новой строки типа так
//data class ResponseNews(
//    val status: String,
//    val totalResults: Int,
//    val articles: List<News>,
//)

data class ResponseNews(val status: String, val totalResults: Int, val articles: List<News>)
