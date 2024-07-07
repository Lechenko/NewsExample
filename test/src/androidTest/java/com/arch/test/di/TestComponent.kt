package com.arch.test.di

import android.content.Context
import com.arch.test.AppTest
import com.arch.test.AppTestComponent
import com.arch.test.TestNews
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [TestModule::class])
interface TestComponent : AppTestComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestComponent
    }
    fun inject(test: TestNews)
}