package com.arch.dependency

import dagger.android.support.DaggerApplication
import timber.log.Timber

abstract class BaseApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.plant(Timber.DebugTree())
    }

    abstract fun onCreateApp()
}