package com.flypika.mvvmproject.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flypika.mvvmproject.DataRepository
import com.flypika.mvvmproject.databinding.FragmentNewsListBinding
import com.flypika.mvvmproject.viewmodel.NewsListViewModel
import com.flypika.mvvmproject.model.News
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsListFragment : Fragment() {
    companion object {
        const val TAG = "NewsListViewModel"
    }

    private val viewModel: NewsListViewModel by viewModels {
        MyFactory(
            DataRepository.getInstance(),
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
    }
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        binding.newsList.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(newsClickCallback)
        binding.newsList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi(viewModel.news)
        viewModel.updateNews()
    }

    private fun subscribeUi(liveData: LiveData<List<News>>) {
        liveData.observe(viewLifecycleOwner) {
            Log.i(NewsListViewModel.TAG, "Submit list")
            adapter.submitList(it)
        }
    }

    private val newsClickCallback = object : NewsClickCallback {
        override fun onClick(position: Int) {
            viewModel.changeMark(position)
        }

    }

    class MyFactory(
        private val repository: DataRepository,
        private val ioScheduler: Scheduler,
        private val uiScheduler: Scheduler
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsListViewModel(repository, ioScheduler, uiScheduler) as T
        }
    }

}