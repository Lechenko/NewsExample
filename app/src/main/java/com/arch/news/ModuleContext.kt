package com.arch.news

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object  ModuleContext {

     @Singleton
     @Provides
     fun contextApp(app : App) : Context = app.applicationContext

}