package com.arch.presentation.router

import androidx.drawerlayout.widget.DrawerLayout
import com.arch.presentation.router.componentNav.ComponentNav
import com.arch.presentation.router.componentNav.NavXML
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

 class Router @Inject constructor(
    private val view: IRouter.IRouterActivity,
    activity: DaggerAppCompatActivity) : BaseRouter(ComponentNav(NavXML(activity))), IRouter {

    override fun init(drawerLayout: DrawerLayout) {
        initDrawer(drawerLayout)

    }
    override fun onStartView() {

    }

    override fun onBackPressed() = super.backPressedRouter()


    override fun onStopView() {

    }

    override fun transaction(cmd: String, obj: Any) {
        super.byTransaction(cmd,obj)
    }

    override fun transaction(cmd: String) {
        this.transaction(cmd, "")
    }


    override fun restartApp() = super.restartAppBase()

    override fun isProgress(flag: Boolean) = view.isProgress(flag)


    override fun removeAllFrag() {
        super.removeAllFrag()
    }


    override fun openDrawer() = super.openDrawer()

    override fun closeDrawer() = super.closeDrawer()

    override fun navDrawer(cmd: String) = super.navDrawer(cmd)



    override fun popBackStack() {
        closeDrawer()
        super.popBackStackRouter()
    }

}