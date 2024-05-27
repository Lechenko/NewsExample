package com.arch.presentation.fragment.search

import com.arch.portdomain.model.StateFlow
import com.arch.presentation.base.BaseVM
import com.arch.presentation.base.IState
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchVM @Inject constructor(
    private val router : IRouter
): BaseVM(),IState {

    override fun state(): Observable<StateFlow> = Observable.defer {
        publisherStateView()
    }.observeOn(provideSchedulersMain())
     fun onClickDataFrom() {

    }

     fun onClickDataTo() {

    }

     fun onClickCancel() {

    }

     fun onClickOk() {

    }

     fun openDrawer() {
     router.openDrawer()
    }

     fun onClickCountry(position: Int) {

    }

    fun onDestroyView() {
       // useCase.stopCase()
        disposePublisher()
    }
}