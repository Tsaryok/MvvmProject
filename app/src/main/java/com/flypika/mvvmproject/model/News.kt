package com.flypika.mvvmproject.model


data class News(
        val author: String,
        val title: String,
        val description: String,
        val urlToImage: String,
        val isMarked: Boolean = false
){
    constructor(news: News, isMarked: Boolean):
            this(news.author, news.title, news.description, news.urlToImage, isMarked)
}
