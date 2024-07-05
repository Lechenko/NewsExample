package com.arch.presentation.base

import androidx.lifecycle.ViewModel
import com.arch.portdomain.model.StateLayer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseVM : ViewModel()  {
    private var publisher: PublishSubject<StateLayer>? = PublishSubject.create()
    private var observeThread: Scheduler = AndroidSchedulers.mainThread()
    protected fun publisherStateView(): Observable<StateLayer> {
        return publisher ?: PublishSubject.create()
    }
    protected open fun provideSchedulersMain() : Scheduler = observeThread
    protected fun onNext(stateLayer: StateLayer) = publisher?.onNext(stateLayer)

    protected fun disposePublisher(){
        publisher?.onComplete()
        publisher = null
    }
    protected fun onError(msg : String) = msg.let {publisher?.onError(Throwable(it))}
}