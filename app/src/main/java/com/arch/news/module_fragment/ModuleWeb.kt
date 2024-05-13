package com.arch.news.module_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arch.domain.web.WebUseCase
import com.arch.news.scope.FragmentScope
import com.arch.news.scope.ViewModelKey
import com.arch.portdomain.ViewModelFactory
import com.arch.portdomain.web.IWebUseCase
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.web.WebVM
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleWeb {
    @FragmentScope
    @Binds
    abstract fun bindWebUseCase(usecase : WebUseCase) : IWebUseCase.UseCaseWeb


    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(WebVM::class)
        fun bindMyViewModel(factory: ViewModelFactory, fragment: News): ViewModel =
            ViewModelProvider(fragment, factory)[WebVM::class.java]
    }
}