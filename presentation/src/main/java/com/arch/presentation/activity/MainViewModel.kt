package com.arch.presentation.activity

import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModel
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import javax.inject.Inject

class MainViewModel @Inject constructor(private val router : IRouter): ViewModel() {
     fun funBindingRouter(): IRouter = router
     fun initDrawerLayout(drawer: DrawerLayout) = router.init(drawer)

     fun startFragmentMain(){
          router.transaction(ConstRouter.NEWS_GROUP_FRAGMENT.route)
     }

     override fun onCleared() {
          router.onStopView()
          super.onCleared()
     }
}