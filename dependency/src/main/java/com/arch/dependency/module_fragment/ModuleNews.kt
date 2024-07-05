package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.presentation.base.ViewModelFactory
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.news.NewsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleNews {
    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(NewsViewModel::class)
        fun bindNewsViewModel(factory: ViewModelFactory, fragment: News): NewsViewModel =
            ViewModelProvider(fragment, factory)[NewsViewModel::class.java]
    }

}