package com.arch.presentation.activity


import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import com.arch.portdomain.main.IMainUseCase
import com.arch.portdomain.model.StateFlow
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

     fun state() : Observable<StateFlow> = useCase.stateDomain()
          .observeOn(provideSchedulersMain())

     fun startFragmentMain() = router.transaction(ConstRouter.NEWS_GROUP_FRAGMENT.route)

     override fun onCleared() {
          router.onStopView()
          super.onCleared()
     }
     fun onDestroyView() = useCase.stopCase()

}