package com.arch.test.di

import com.arch.dependency.AppModule
import com.arch.dependency.module_domain.ModuleFavoritesDomain
import com.arch.dependency.module_domain.ModuleNewsDomain
import com.arch.dependency.module_domain.ModuleNewsGroupDomain
import com.arch.test.ModuleContextTest
import dagger.Module
import dagger.android.AndroidInjectionModule



@Module(includes = [AndroidInjectionModule::class,ModuleContextTest::class,AppModule::class,
    ModuleFavoritesDomain::class, ModuleNewsDomain::class, ModuleNewsGroupDomain::class])
abstract class TestModule {

}