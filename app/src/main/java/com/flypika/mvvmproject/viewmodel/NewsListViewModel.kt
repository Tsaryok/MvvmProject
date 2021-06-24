package com.flypika.mvvmproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flypika.mvvmproject.DataRepository
import com.flypika.mvvmproject.model.News
import kotlinx.coroutines.launch
//Ctrl + Alt + L
class NewsListViewModel : ViewModel() {

    private val repository = DataRepository.getInstance()
    private val _news = MutableLiveData<List<News>>()
    val news : LiveData<List<News>> = _news

    init {
        repository.getNewsList { news_list ->
            viewModelScope.launch { // зачем?
                if (news_list != null) {
                    _news.value = news_list
                }
            }
        }
    }


}