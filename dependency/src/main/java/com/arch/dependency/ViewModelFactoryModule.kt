package com.arch.dependency

import com.arch.presentation.base.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {
    @Binds
    public  abstract fun bindViewModelFactory(factory: NewsViewModelFactory): ViewModelFactory
}

