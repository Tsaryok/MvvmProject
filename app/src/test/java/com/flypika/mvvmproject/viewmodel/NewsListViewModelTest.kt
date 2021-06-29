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

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    val io = Schedulers.trampoline()
    val ui = Schedulers.trampoline()

    lateinit var list: List<News>

    lateinit var repository: DataRepository

    lateinit var viewModel: NewsListViewModel

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
        //verify(observer).onChanged(list) зачем закомментил?
        // добавь еще verifyNoMoreInteractions для моков, чтобы была уверенность, что больше
        // никаких дейсвтий не происходит
    }

    @Test
    fun changeMark() {
        viewModel.updateNews()
        verify(repository).getNewsList() // этот момент ты тестишь в предыдущем тесте. Думаю нет смысла здесь это друблировать
        assertEquals(viewModel.news.value, list) // аналогично
        val prevList = viewModel.news.value
        assertNotNull(prevList)
        viewModel.changeMark(1)
        // более наглядно было бы составить список, который мы ожидаем получить
        // после совершения каких либо действий.
        // Так заодно сможешь потестить приход нового списка в observer
        // как в предыдущем тесте, хотя в нем ты это зачем то закомментил
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