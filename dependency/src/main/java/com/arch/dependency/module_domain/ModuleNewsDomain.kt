package com.arch.dependency.module_domain

import com.arch.dependency.scope.FragmentScope
import com.arch.domain.news.NewsUseCase
import com.arch.portdomain.news.INewsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class ModuleNewsDomain {
    @FragmentScope
    @Binds
    abstract fun bindNewsUseCase(useCase: NewsUseCase): INewsUseCase.UseCaseNews
}