package com.arch.presentation.fragment.web


import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.web.IWebUseCase
import com.arch.presentation.base.BaseVM
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WebVM @Inject constructor(
    private val router : IRouter,
    private val useCase : IWebUseCase.UseCaseWeb
): BaseVM() {

    fun state(): Observable<StateFlow> = Observable.defer {
        Observable.merge(
            publisherStateView(), useCase.stateDomain()
        )
    }.compose(applyObservableSchedulers())

     fun menu() {
        router.openDrawer()
    }

     fun saveNews(item : NewsModel) {
        useCase.saveNews(item)
    }

//     fun successSave() {
//        view.showMessage("save ok")
//    }
//
//     fun onMessage(message: String) {
//        view.showMessage(message)
//    }

}