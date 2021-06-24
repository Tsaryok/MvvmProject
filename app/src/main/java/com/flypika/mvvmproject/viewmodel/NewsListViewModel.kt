package com.flypika.mvvmproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flypika.mvvmproject.DataRepository
import com.flypika.mvvmproject.model.News
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {
    companion object {
        const val TAG = "NewsListViewModel"
    }

    private val repository = DataRepository.getInstance()
    private val _news = MutableLiveData<ArrayList<News>>()
    val news: LiveData<ArrayList<News>> = _news

    init {
        repository.getNewsList { news_list ->
            if (news_list != null) {
                _news.value = news_list
            }
        }
    }

    fun changeMark(position: Int) {
        Log.i(TAG, "Changing mark: ${news.value!![position].isMarked}")
        val news_list = _news.value
        news_list!![position] = News(news_list[position], news_list[position].isMarked.not())
        _news.value = news_list!!
        Log.i(TAG, "Changed mark: ${news_list[position].isMarked}")
    }
}