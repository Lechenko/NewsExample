package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.domain.web.WebUseCase
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.presentation.base.ViewModelFactory
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
        fun bindWebViewModel(factory: ViewModelFactory, fragment: News): WebVM =
            ViewModelProvider(fragment, factory)[WebVM::class.java]
    }
}