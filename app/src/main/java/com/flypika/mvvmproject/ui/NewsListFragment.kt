package com.flypika.mvvmproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.flypika.mvvmproject.viewmodel.NewsListViewModel
import com.flypika.mvvmproject.databinding.NewsListFragmentBinding
import com.flypika.mvvmproject.model.News

class NewsListFragment : Fragment(){

    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var mBinding: NewsListFragmentBinding
    private lateinit var mNewsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = NewsListFragmentBinding.inflate(inflater, container, false)
        mBinding.newsList.layoutManager = LinearLayoutManager(context)
        mNewsAdapter = NewsAdapter()
        mBinding.newsList.adapter = mNewsAdapter

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi(viewModel.news)
    }

    private fun subscribeUi(liveData: LiveData<List<News>>){
        liveData.observe(viewLifecycleOwner){
            mNewsAdapter.submitList(it)
        }
    }
}