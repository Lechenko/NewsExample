package com.arch.presentation.base

import androidx.lifecycle.ViewModel
import com.arch.portdomain.model.StateFlow
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseVM : ViewModel()  {
    private var publisher: PublishSubject<StateFlow>? = PublishSubject.create()
    private var jobThread: Scheduler = Schedulers.io()
    private var observeThread: Scheduler = AndroidSchedulers.mainThread()
    protected fun publisherStateView(): Observable<StateFlow> {
        return publisher ?: PublishSubject.create()
    }

    protected fun onNext(stateFlow: StateFlow) = publisher?.onNext(stateFlow)

    protected fun onError(msg : String) = msg.let {publisher?.onError(Throwable(it))}

    protected open fun <T : Any> applySingleSchedulers(): SingleTransformer<T, T> =
        SingleTransformer {
            it.subscribeOn(jobThread)
                .observeOn(observeThread)
        }

    protected open fun <T : Any> applyFlowableSchedulers(): FlowableTransformer<T, T> =
        FlowableTransformer {
            it.subscribeOn(jobThread)
                .observeOn(observeThread)
        }

    protected open fun <T : Any> applyObservableSchedulers(): ObservableTransformer<T, T> =
        ObservableTransformer {
            it.subscribeOn(jobThread)
                .observeOn(observeThread)
        }

    protected open fun applyCompletableSchedulers(): CompletableTransformer =
        CompletableTransformer {
            it.subscribeOn(jobThread)
                .observeOn(observeThread)
        }

}