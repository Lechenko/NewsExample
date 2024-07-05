package com.arch.presentation.activity


import androidx.drawerlayout.widget.DrawerLayout
import com.arch.portdomain.main.IMainUseCase
import com.arch.portdomain.model.StateLayer
import com.arch.presentation.base.BaseVM
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class MainViewModel @Inject constructor(
     private val router : IRouter,
     private val useCase : IMainUseCase):  BaseVM() {
     fun funBindingRouter(): IRouter = router

     fun initDrawerLayout(drawer: DrawerLayout) = router.init(drawer)

     fun state() : Observable<StateLayer> = useCase.byDomain()
          .observeOn(provideSchedulersMain())

     fun startFragmentMain() = router.transaction(ConstRouter.NEWS_GROUP_FRAGMENT.route)

     override fun onCleared() {
          router.onStopView()
          super.onCleared()
     }
     fun onDestroyView() {
          useCase.stopCase()
          router.onStopView()
     }

}