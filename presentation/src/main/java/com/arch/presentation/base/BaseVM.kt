package com.arch.presentation.base

import androidx.lifecycle.ViewModel
import com.arch.portdomain.model.StateFlow
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseVM : ViewModel()  {
    private var publisher: PublishSubject<StateFlow>? = PublishSubject.create()
    protected fun publisherStateView(): Observable<StateFlow> {
        return publisher ?: PublishSubject.create()
    }

    protected fun onNext(stateFlow: StateFlow) = publisher?.onNext(stateFlow)

    protected fun onError(msg : String) = msg.let {publisher?.onError(Throwable(it))}

}