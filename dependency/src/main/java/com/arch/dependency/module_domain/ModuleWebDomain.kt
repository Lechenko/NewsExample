package com.arch.dependency.module_domain

import com.arch.dependency.scope.FragmentScope
import com.arch.domain.web.WebUseCase
import com.arch.portdomain.web.IWebUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class ModuleWebDomain {
    @FragmentScope
    @Binds
    abstract fun bindWebUseCase(usecase : WebUseCase) : IWebUseCase.UseCaseWeb
}