package com.arch.test

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object  ModuleContextTest {
     @Singleton
     @Provides
     fun contextApp(app : AppTest) : Context = app.applicationContext

}