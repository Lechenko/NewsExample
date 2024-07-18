package com.arch.presentation.fragment.favorites.adapter

import android.view.View
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.base.BaseViewHolder
import com.arch.presentation.databinding.ItemFavoritesBinding
import com.arch.presentation.fragment.favorites.NewsFavoritesVM
import com.arch.presentation.glide.GlideApp
import com.arch.presentation.util.StringUtils
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FavoritesViewHolder constructor(private val view : View,private val viewModel : NewsFavoritesVM)
    : BaseViewHolder<ItemFavoritesBinding>(view)  {

    init {
        binding?.let { it.event = viewModel }
    }

    fun bind(item : NewsModel){
        binding?.let {bind ->
            bind.item = item
            item.urlToImage?.let {
                GlideApp.with(view)
                    .load(it)
                    .fitCenter()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .override(300, 200)
                    .into(bind.ivItemFavoritesImage)
            }
            item.title?.let { bind.tvItemFavoritesTitle.text = it}
            item.description?.let { bind.tvItemFavoritesDescription.text = it}
            item.publishedAt?.let { bind.tvFavoritesTime.text = StringUtils.dateFormat(it) }
        }
    }
}