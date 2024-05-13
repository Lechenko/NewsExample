package com.arch.news.module_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arch.domain.news_group.GroupUseCase
import com.arch.news.scope.FragmentScope
import com.arch.news.scope.ViewModelKey
import com.arch.portdomain.ViewModelFactory
import com.arch.portdomain.news_group.IGroupsUseCase
import com.arch.presentation.fragment.group.NewsGroupVM
import com.arch.presentation.fragment.news.News
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleNewsGroup {
    @FragmentScope
    @Binds
    abstract fun bindUseCaseGroup(useCase: GroupUseCase) : IGroupsUseCase.UseCaseGroup

    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(NewsGroupVM::class)
        fun bindMyViewModel(factory: ViewModelFactory, fragment: News): ViewModel =
            ViewModelProvider(fragment, factory)[NewsGroupVM::class.java]
    }

}