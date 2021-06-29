package com.flypika.mvvmproject.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flypika.mvvmproject.databinding.ItemNewsBinding
import com.flypika.mvvmproject.model.News

class NewsAdapter(private val callback: NewsClickCallback?) : ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback) {

    companion object {
        const val TAG = "NewsAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
            RecyclerView.ViewHolder(binding.root) {
        private var currentPosition = 0

        init {
            binding.root.setOnClickListener {
                Log.i(TAG, "Click on item")
                callback?.onClick(currentPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(_position: Int) {
            Log.i(TAG, "Bind")
            currentPosition = _position
            if (getItem(currentPosition).isMarked) {
                binding.root.setCardBackgroundColor(Color.GREEN)
            } else {
                binding.root.setCardBackgroundColor(Color.WHITE)
            }
            binding.title.text = getItem(currentPosition).title
            binding.author.text = getItem(currentPosition).author
            Glide.with(binding.image)
                    .load(getItem(currentPosition).urlToImage)
                    .into(binding.image)
        }
    }

    object NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            Log.i(TAG, "areContentsTheSame")
            return oldItem == newItem
        }
    }
}