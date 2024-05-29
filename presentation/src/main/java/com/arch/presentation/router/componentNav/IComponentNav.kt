package com.arch.presentation.router.componentNav

import androidx.drawerlayout.widget.DrawerLayout

interface IComponentNav {
    fun initDrawerLayout(drawerLayout: DrawerLayout)

    fun onBackPressed()

    fun transaction(cmd: String, obj: Any)

    fun transaction(cmd: String)

    fun restartApp()

    fun removeAllFrag()

    fun openDrawer()

    fun closeDrawer()

    fun navDrawer(cmd: String)

    fun popBackStack()

    fun finishActivity()
}