package com.arch.test

import com.arch.dependency.BaseApplication
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppTest : BaseApplication() {

    override fun onCreateApp() {

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppTestComponent.factory()
            .create(this)
}