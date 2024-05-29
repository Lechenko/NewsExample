package com.arch.presentation.router.componentNav

import androidx.drawerlayout.widget.DrawerLayout

class ComponentNav(private val component : IComponentNav) : IComponentNav{
    override fun initDrawerLayout(drawerLayout: DrawerLayout) {
        component.initDrawerLayout(drawerLayout)
    }

    override fun onBackPressed() {
        component.onBackPressed()
    }



    override fun transaction(cmd: String, obj: Any) {
        component.transaction(cmd,obj)
    }

    override fun transaction(cmd: String) {
        component.transaction(cmd)
    }

    override fun restartApp() {
        component.restartApp()
    }

    override fun removeAllFrag() {
        component.removeAllFrag()
    }

    override fun openDrawer() {
        component.openDrawer()
    }

    override fun closeDrawer() {
        component.closeDrawer()
    }

    override fun navDrawer(cmd: String) {
        component.navDrawer(cmd)
    }

    override fun popBackStack() {
        component.popBackStack()
    }

    override fun finishActivity() {
       component.finishActivity()
    }

}