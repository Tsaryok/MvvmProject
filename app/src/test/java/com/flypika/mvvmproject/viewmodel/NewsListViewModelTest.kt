package com.flypika.mvvmproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.flypika.mvvmproject.DataRepository
import com.flypika.mvvmproject.model.News
import com.flypika.mvvmproject.model.ResponseNews
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.awaitility.kotlin.await
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class NewsListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    val list = listOf(
        News("BBC", "Title1", "News1", "https:..."),
        News("The Times", "Title2", "News2", "https:..."),
        News("The Daily Telegraph", "Title3", "News3", "https:...")
    )

    val repository = mock<DataRepository> {
        on {
            getNewsList()
        } doReturn Observable.just(ResponseNews("ok", 3, list))
    }

    val io = Schedulers.io()
    val ui = Schedulers.newThread()

    val viewModel = NewsListViewModel(repository, io, ui)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun updateNews() {
        val observer = mock<Observer<List<News>>>()
        viewModel.news.observeForever(observer)
        viewModel.updateNews()
        verify(repository).getNewsList()
        assertEquals(viewModel.news.value, list)
        verify(observer).onChanged(list)
    }

    @Test
    fun changeMark() {
        viewModel.updateNews()
        verify(repository).getNewsList()
        assertEquals(viewModel.news.value, list)
        val prevList = viewModel.news.value
        assertNotNull(prevList)
        viewModel.changeMark(1)
        prevList!!.forEachIndexed { index, value ->
            if (viewModel.news.value!![index] != value) {
                assertEquals(index, 1)
                assertNotEquals(value.isMarked, viewModel.news.value!![index].isMarked)
            } else {
                assertNotEquals(index, 1)
            }
        }
    }
}