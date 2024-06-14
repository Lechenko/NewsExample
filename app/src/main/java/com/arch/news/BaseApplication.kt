package com.arch.news


import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

abstract class BaseApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    abstract fun onCreateApp()
    abstract fun  appInject() : App
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory()
            .create(appInject())
}