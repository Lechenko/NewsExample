package com.arch.news

import com.arch.portdomain.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: NewsViewModelFactory): ViewModelFactory

}