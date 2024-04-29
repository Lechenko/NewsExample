package com.arch.presentation.activity

import androidx.drawerlayout.widget.DrawerLayout
import com.arch.portdomain.main.IMainUseCase
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor(private val view : IMainView.View,
                                        private val useCase : IMainUseCase,
                                        private val router : IRouter)
    : IMainView.Presenter {
    private var disposable  : CompositeDisposable ?= null
    override fun initDrawerLayout(drawer: DrawerLayout) {
       router.init(drawer)
    }

    override fun funBindingRouter(): IRouter = router

    override fun startView() {
        disposable = CompositeDisposable()
        router.transaction(ConstRouter.NEWS_GROUP_FRAGMENT.route)
    }

    override fun stopView() {
        disposable?.clear()
    }

    override fun pauseView() {

    }

    override fun destroyView() {
        useCase.stopCase()
        disposable?.dispose()
        disposable = null
    }
}