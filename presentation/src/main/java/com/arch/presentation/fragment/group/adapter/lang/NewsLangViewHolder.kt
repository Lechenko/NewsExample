package com.arch.presentation.fragment.group.adapter.lang

import android.view.View
import androidx.core.content.ContextCompat
import com.arch.presentation.R
import com.arch.presentation.base.BaseViewHolder
import com.arch.presentation.databinding.ItemLangBinding
import com.arch.presentation.fragment.group.NewsGroupVM
import com.arch.presentation.util.Language
import timber.log.Timber

class NewsLangViewHolder(private val view : View,private val viewModel: NewsGroupVM )
    : BaseViewHolder<ItemLangBinding>(view){
    init {
        binding?.let { it.event = viewModel }
    }

    fun bind(langKey : String, langValue : String){
        binding?.let {
            it.tvItemLanguage.text = langKey
            it.tvItemLanguage.setOnClickListener { _ ->
                Language.flag = langKey
                it.tvItemLanguage.setBackgroundColor(
                    ContextCompat.getColor(view.context,R.color.select_language_ok)
                )
                viewModel.onClickLanguage(langValue)
            }
            Timber.tag(NewsLangViewHolder::class.simpleName.toString()).e(Language.flag)
            it.tvItemLanguage.setBackgroundColor(
                if (Language.isSelectLang(langKey))
                        ContextCompat.getColor(view.context,R.color.select_language_ok) else
                    ContextCompat.getColor(view.context,R.color.select_language_not)
            )
        }
    }
}