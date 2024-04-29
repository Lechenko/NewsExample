package com.arch.presentation.activity

import androidx.drawerlayout.widget.DrawerLayout
import com.arch.presentation.base.BasePresenter
import com.arch.presentation.base.BaseViewActivityContract
import com.arch.presentation.router.IRouter

interface IMainView {
    interface View : BaseViewActivityContract {

    }

    interface Presenter : BasePresenter {
        fun initDrawerLayout(drawer : DrawerLayout)
        fun funBindingRouter() : IRouter
    }
}