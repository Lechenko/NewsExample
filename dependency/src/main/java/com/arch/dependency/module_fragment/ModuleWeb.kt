package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.presentation.base.ViewModelFactory
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.web.WebVM
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleWeb {
    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(WebVM::class)
        fun bindWebViewModel(factory: ViewModelFactory, fragment: News): WebVM =
            ViewModelProvider(fragment, factory)[WebVM::class.java]
    }
}