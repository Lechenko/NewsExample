package com.arch.test.util

import android.annotation.SuppressLint
import androidx.test.espresso.IdlingResource
import com.arch.presentation.base.BaseFragment
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean


class NewsIdlingResource(private val baseFragment : BaseFragment<*,*>)
    : IdlingResource {
    private val isIdleNow = AtomicBoolean(true)
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = "MyIdlingResource"

    override fun isIdleNow(): Boolean = isIdleNow.get()

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    @SuppressLint("CheckResult")
    fun performAsyncOperation() {
        isIdleNow.set(false)
        baseFragment.testStatusVM()?.let {
            val subject = Single.defer{it}
            subject.subscribe({
                isIdleNow.set(true)
                callback?.onTransitionToIdle()
            },{
                Timber.tag("Test subject status VM").e("error s%",it.message)
            })
        }
    }
}