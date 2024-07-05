package com.arch.news

import com.arch.dependency.BaseApplication
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber


class App : BaseApplication() {


    override fun onCreateApp() {

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory()
            .create(this)

}