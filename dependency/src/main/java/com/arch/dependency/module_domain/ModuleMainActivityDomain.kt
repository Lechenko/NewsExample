package com.arch.dependency.module_domain


import com.arch.dependency.scope.ActivityScope
import com.arch.domain.main.MainUseCase
import com.arch.portdomain.main.IMainUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class ModuleMainActivityDomain {
    @ActivityScope
    @Binds
    abstract fun bindMainUseCase(useCase: MainUseCase): IMainUseCase
}