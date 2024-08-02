package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.presentation.base.ViewModelFactory
import com.arch.presentation.fragment.group.NewsGroup
import com.arch.presentation.fragment.group.NewsGroupVM
import com.arch.presentation.fragment.news.News
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleNewsGroup {
    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(NewsGroupVM::class)
        fun bindNewsGroupViewModel(factory: ViewModelFactory, fragment: NewsGroup): NewsGroupVM =
            ViewModelProvider(fragment, factory)[NewsGroupVM::class.java]
    }

}