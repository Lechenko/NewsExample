package com.arch.dependency.module_domain

import com.arch.dependency.scope.FragmentScope
import com.arch.domain.news_group.GroupUseCase
import com.arch.portdomain.news_group.IGroupsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class ModuleNewsGroupDomain {
    @FragmentScope
    @Binds
    abstract fun bindUseCaseGroup(useCase: GroupUseCase) : IGroupsUseCase.UseCaseGroup
}