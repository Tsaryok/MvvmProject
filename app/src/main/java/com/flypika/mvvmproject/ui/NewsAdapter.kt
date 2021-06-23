package com.flypika.mvvmproject.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flypika.mvvmproject.databinding.NewsItemBinding
import com.flypika.mvvmproject.model.News

class NewsAdapter: ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback) {

    companion object{
        const val TAG = "NewsAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val mBinding = NewsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NewsViewHolder(private val mBinding: NewsItemBinding):
        RecyclerView.ViewHolder(mBinding.root){
        private var isMarked = false
        init {
            mBinding.root.setOnClickListener {
                Log.i(TAG, "Click on item")
                isMarked = if (isMarked) {
                    mBinding.root.setCardBackgroundColor(Color.WHITE)
                    false
                }else {
                    mBinding.root.setCardBackgroundColor(Color.GREEN)
                    true
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(news: News){
            mBinding.title.text = news.title
            mBinding.author.text = news.author
        }
    }

    object NewsDiffCallback: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.author == newItem.author
                    && oldItem.description == newItem.description
                    && oldItem.title == newItem.title
                    && oldItem.urlToImage == newItem.urlToImage
        }
    }
}