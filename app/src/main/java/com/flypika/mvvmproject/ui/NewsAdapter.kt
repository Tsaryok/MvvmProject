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
import com.bumptech.glide.Glide
import com.flypika.mvvmproject.databinding.NewsItemBinding
import com.flypika.mvvmproject.model.News

// Ctrl + Alt + L

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

    // мы не юзаем m как префикс для приватных полей
    // в котлине уж точно
    class NewsViewHolder(private val mBinding: NewsItemBinding):
        RecyclerView.ViewHolder(mBinding.root){
        lateinit var currentNews: News
        init {
            // логика на вью слое, вьюмодель даже не узнает, что был клик на айтем
            // на самом деле это мое задание говно, нужно было придумать что-то,
            // чтобы по клику еще и вьюмодель что-то делала, но ладно)
            // сделай так, чтобы обновление состояния списка происходило во вьюмодели
            mBinding.root.setOnClickListener {
                Log.i(TAG, "Click on item")
                currentNews.isMarked = if (currentNews.isMarked) {
                    mBinding.root.setCardBackgroundColor(Color.WHITE)
                    false
                }else{
                    mBinding.root.setCardBackgroundColor(Color.GREEN)
                    true
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(news: News){
            currentNews = news
            if (currentNews.isMarked) {
                mBinding.root.setCardBackgroundColor(Color.GREEN)
            }else{
                mBinding.root.setCardBackgroundColor(Color.WHITE)
            }
            mBinding.title.text = news.title
            mBinding.author.text = news.author
            // я подгрузил фотку новости и верстка чутка поехала
            // сделай, чтобы нормально было
            Glide.with(mBinding.image)
                .load(news.urlToImage)
                .into(mBinding.image)
        }
    }

    object NewsDiffCallback: DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            // неверно, в этом колбэке сравнивают айдишки айтемов
            // если их нет, то наверное лучше отказаться от DiffUtil
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            // а вот здесь как раз удобно использовать equals (==)
            // разумеется при условии что News станет data классом
            return oldItem.author == newItem.author
                    && oldItem.description == newItem.description
                    && oldItem.title == newItem.title
                    && oldItem.urlToImage == newItem.urlToImage
        }
    }
    // почитай доку DiffUtil.ItemCallback, зачем именно нужен каждый колбэк
}