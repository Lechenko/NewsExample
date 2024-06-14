package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.domain.news_group.GroupUseCase
import com.arch.presentation.base.ViewModelFactory
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
        fun bindNewsGroupViewModel(factory: ViewModelFactory, fragment: News): NewsGroupVM =
            ViewModelProvider(fragment, factory)[NewsGroupVM::class.java]
    }

}