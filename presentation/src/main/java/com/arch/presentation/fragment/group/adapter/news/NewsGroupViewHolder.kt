package com.arch.presentation.fragment.group.adapter.news


import android.view.View
import androidx.core.content.ContextCompat
import com.arch.portdomain.model.NewsGroupModel
import com.arch.presentation.R
import com.arch.presentation.base.BaseViewHolder
import com.arch.presentation.databinding.ItemGroupBinding
import com.arch.presentation.fragment.group.NewsGroupVM
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy



class NewsGroupViewHolder(private val  view : View,private val viewModel: NewsGroupVM)
    : BaseViewHolder<ItemGroupBinding>(view){

    init {
        binding?.let { it.event = viewModel }
    }

    fun bind(item : NewsGroupModel) =
        binding?.let {
            Glide.with(view.context)
                .load(ContextCompat.getDrawable(view.context, R.drawable.news_logo_rv))
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .override(300, 200)
                .into(it.rivCategoryLogoDomain)
                item.name?.let { name -> it.tvCategoryDescription.text = name }
                it.contentItem.setOnClickListener {
                    viewModel.selectedItem(item)
                }
            }

}