package com.arch.presentation.router

import androidx.drawerlayout.widget.DrawerLayout
import com.arch.presentation.R
import com.arch.presentation.router.componentNav.IComponentNav


abstract class BaseRouter(private val component: IComponentNav) {
    fun initDrawer(drawerLayout: DrawerLayout) = component.initDrawerLayout(drawerLayout)
    protected fun backPressedRouter() {
            component.onBackPressed()
    }

    protected open fun popBackStackRouter() {
        component.popBackStack()
    }


    protected fun restartAppBase() {
        component.restartApp()
    }

    protected fun content() : Int = R.id.content

    fun finishActivity(){
      component.finishActivity()
    }

    protected open fun removeAllFrag(){
        component.removeAllFrag()
    }

    protected open fun navDrawer(cmd: String){
        component.navDrawer(cmd)
    }

    fun byTransaction(cmd: String, obj: Any) = component.transaction(cmd, obj)

    protected open fun openDrawer() = component.openDrawer()

    protected open fun closeDrawer() = component.closeDrawer()


}