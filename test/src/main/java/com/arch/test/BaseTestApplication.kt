package com.arch.test


import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

abstract class BaseTestApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    abstract fun onCreateApp()
    abstract fun  appInject() : AppTest
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppTestComponent.factory()
            .create(appInject())
}