package com.flypika.mvvmproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.flypika.mvvmproject.DataRepository
import com.flypika.mvvmproject.model.News
import com.flypika.mvvmproject.model.ResponseNews
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(JUnit4::class)
class NewsListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val io = Schedulers.trampoline()
    val ui = Schedulers.trampoline()

    lateinit var list: List<News>

    lateinit var repository: DataRepository

    lateinit var viewModel: NewsListViewModel

    lateinit var observer: Observer<List<News>>

    @Before
    fun setUp() {
        list = listOf(
            News("BBC", "Title1", "News1", "https:..."),
            News("The Times", "Title2", "News2", "https:..."),
            News("The Daily Telegraph", "Title3", "News3", "https:...")
        )
        repository = mock<DataRepository> {
            on {
                getNewsList()
            } doReturn Observable.just(ResponseNews("ok", 3, list))
        }
        viewModel = NewsListViewModel(repository, io, ui)
        observer = mock()
        viewModel.news.observeForever(observer)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun updateNews() {
        viewModel.updateNews()
        verify(repository).getNewsList()
        assertEquals(viewModel.news.value, list)
        verify(observer).onChanged(list)
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun changeMark() {
        viewModel.updateNews()
        verify(repository).getNewsList()
        viewModel.changeMark(1)
        val actual = listOf(
            News("BBC", "Title1", "News1", "https:..."),
            News("The Times", "Title2", "News2", "https:...", true),
            News("The Daily Telegraph", "Title3", "News3", "https:...")
        )
        verify(observer).onChanged(actual)
    }
}