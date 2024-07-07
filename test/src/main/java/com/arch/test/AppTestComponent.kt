package com.arch.test

import com.arch.dependency.AppModule
import com.arch.dependency.BuildersModuleActivity
import com.arch.dependency.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ModuleContextTest::class,
    AppModule::class,
    BuildersModuleActivity::class,
    ViewModelFactoryModule::class])
interface AppTestComponent : AndroidInjector<AppTest> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<AppTest>{
        override fun create(@BindsInstance instance: AppTest): AppTestComponent
    }
}