package com.arch.presentation.fragment.search

import com.arch.portdomain.model.StateLayer
import com.arch.presentation.base.BaseVM
import com.arch.presentation.base.IState
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SearchVM @Inject constructor(
    private val router : IRouter
): BaseVM(),IState {

    override fun state(): Observable<StateLayer> = Observable.defer {
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

    //TODO inprogress
     fun onClickCountry(position: Int) {

    }

    fun onDestroyView() {
       // useCase.stopCase()
        disposePublisher()
    }
}