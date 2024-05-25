package com.arch.presentation.fragment.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.R
import com.arch.presentation.fragment.news.NewsViewModel

class NewsAdapter(private val viewModel : NewsViewModel) : RecyclerView.Adapter<NewsViewHolder>() {
    private var mList  = mutableListOf<NewsModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false), viewModel)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       if (mList.size > 0)holder.bind(mList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : List<NewsModel>){
        mList.addAll(list)
        notifyDataSetChanged()
    }

}