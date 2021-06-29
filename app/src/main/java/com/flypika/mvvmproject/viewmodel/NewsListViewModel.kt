package com.flypika.mvvmproject.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flypika.mvvmproject.DataRepository
import com.flypika.mvvmproject.model.News
import com.flypika.mvvmproject.model.ResponseNews
import io.reactivex.Scheduler

@SuppressLint("CheckResult")
class NewsListViewModel(
    private val repository: DataRepository,
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler
) : ViewModel() {

    companion object {
        const val TAG = "NewsListViewModel"
    }

    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    fun changeMark(position: Int) {
        //Log.i(TAG, "Changing mark.")
        _news.value = _news.value?.mapIndexed { index, value ->
            if (index == position) {
                value.copy(isMarked = !value.isMarked)
            } else {
                value
            }

        }
    }

    fun updateNews() {
        repository.getNewsList()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(this::handleResults, this::handleError)
    }

    private fun handleResults(response: ResponseNews) {
        if (response.status == "ok" && response.totalResults != 0) {
            _news.value = response.articles
        }
    }

    private fun handleError(t: Throwable) {
        Log.e(TAG, t.toString())
    }
}