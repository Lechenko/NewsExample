package com.arch.presentation.fragment.web


import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateLayer
import com.arch.portdomain.web.IWebUseCase
import com.arch.presentation.base.BaseVM
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class WebVM @Inject constructor(
    private val router: IRouter,
    private val useCase: IWebUseCase.UseCaseWeb
) : BaseVM() {

    fun state(): Observable<StateLayer> = Observable.defer {
        Observable.merge(
            publisherStateView(), useCase.byDomain()
        )
    }.observeOn(provideSchedulersMain())

    fun menu() {
        router.openDrawer()
    }

    fun saveNews(item: NewsModel) {
        useCase.saveNews(item)
    }

    //     fun successSave() {
//        view.showMessage("save ok")
//    }
//
//     fun onMessage(message: String) {
//        view.showMessage(message)
//    }
    fun onDestroyView() {
        useCase.stopCase()
        disposePublisher()
    }
}