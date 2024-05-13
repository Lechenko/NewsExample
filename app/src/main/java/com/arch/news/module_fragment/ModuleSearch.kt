package com.arch.news.module_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arch.news.scope.FragmentScope
import com.arch.news.scope.ViewModelKey
import com.arch.portdomain.ViewModelFactory
import com.arch.presentation.fragment.news.News
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
        fun bindMyViewModel(factory: ViewModelFactory, fragment: News): ViewModel =
            ViewModelProvider(fragment, factory)[SearchVM::class.java]
    }
}