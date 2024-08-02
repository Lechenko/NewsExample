package com.arch.presentation.fragment.news.adapter

import android.view.View
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.base.BaseViewHolder
import com.arch.presentation.databinding.ItemNewsBinding
import com.arch.presentation.fragment.news.NewsViewModel
import com.arch.presentation.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class NewsViewHolder constructor(private val view : View,
                                 private val viewModel: NewsViewModel)
    : BaseViewHolder<ItemNewsBinding>(view) {
    init {
        binding?.let { it.event = viewModel }
    }

    fun bind(item: NewsModel){
        binding?.let {
            item.urlToImage?.let {urlToImage ->
                Glide.with(view.context)
                    .load(urlToImage)
                    .fitCenter()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .override(300, 200)
                    .into(it.ivItemNewsImage)
            }
            item.title?.let {title -> it.tvItemNewsTitle.text = title}
            item.description?.let {description -> it.tvItemNewsDescription.text = description}
            item.publishedAt?.let {publishedAt ->
                it.tvNewsTime.text = StringUtils.dateFormat(publishedAt)}
            it.clNewItem.setOnClickListener{
                viewModel.selectedNews(item)
            }
            it.ivItemNewsFavorites.setOnClickListener {
                viewModel.saveNews(item)
            }
            it.ivNewsShare.setOnClickListener {
                viewModel.shareContent(item)
            }
        }
    }
}