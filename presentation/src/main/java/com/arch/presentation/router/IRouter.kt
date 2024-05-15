package com.arch.presentation.router

import androidx.drawerlayout.widget.DrawerLayout
import io.reactivex.rxjava3.core.Single

interface IRouter {
    fun init(drawerLayout: DrawerLayout)

    fun onStartView()

    fun onBackPressed()

    fun onStopView()

    fun transaction(cmd: String, obj: Any)

    fun transaction(cmd: String)

    fun restartApp()

    fun isProgress(flag: Boolean)

    fun removeAllFrag()

    fun openDrawer()

    fun closeDrawer()

    fun navDrawer(cmd: String)

    fun popBackStack()

    interface IRouterActivity{
         fun isProgress(flag: Boolean)
         fun onMessage(message: String)
    }
}