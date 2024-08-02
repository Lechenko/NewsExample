package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.presentation.base.ViewModelFactory
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.search.Search
import com.arch.presentation.fragment.search.SearchVM
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleSearch {
    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(SearchVM::class)
        fun bindSearchViewModel(factory: ViewModelFactory, fragment: Search): SearchVM =
            ViewModelProvider(fragment, factory)[SearchVM::class.java]
    }
}