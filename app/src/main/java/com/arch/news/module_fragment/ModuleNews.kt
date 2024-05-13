package com.arch.news.module_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arch.domain.news.NewsUseCase
import com.arch.news.scope.FragmentScope
import com.arch.news.scope.ViewModelKey
import com.arch.portdomain.ViewModelFactory
import com.arch.portdomain.news.INewsUseCase
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleNews {
    @FragmentScope
    @Binds
    abstract fun bindNewsUseCase(useCase: NewsUseCase): INewsUseCase.UseCaseNews

    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(NewsViewModel::class)
        fun bindMyViewModel(factory: ViewModelFactory, fragment: News): ViewModel =
            ViewModelProvider(fragment, factory)[NewsViewModel::class.java]
    }

}