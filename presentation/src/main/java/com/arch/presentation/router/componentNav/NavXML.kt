package com.arch.presentation.router.componentNav

import android.content.Intent
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.R
import com.arch.presentation.base.BaseDialogFragment
import com.arch.presentation.fragment.favorites.NewsFavorites
import com.arch.presentation.fragment.group.NewsGroup
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.search.Search
import com.arch.presentation.fragment.web.WebFragment
import com.arch.presentation.router.ConstRouter
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerAppCompatDialogFragment
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.core.Single


class NavXML constructor(private val activity: DaggerAppCompatActivity) : IComponentNav {
    private lateinit var mDrawerLayout: DrawerLayout

    private fun <T : BaseDialogFragment<*>> stepDialog(fragment: T): Single<Int> = Single.defer {
        beginTransactionFragmentDialog(fragment, dagger.android.support.R.id.content)
        Single.create(fragment::setEmitter)
    }

    private fun beginTransactionFragmentNoBackStack(fragment: DaggerFragment, container: Int) =
        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit
            )
            .replace(container, fragment, fragment.javaClass.simpleName)
            .commit()


    private fun beginTransactionFragmentDialog(fragment: DaggerAppCompatDialogFragment, container: Int) =
        activity.supportFragmentManager.beginTransaction()
            .add(container, fragment, fragment.javaClass.simpleName)
            .commit()


    private fun closeFragmentDialog(fragment: DaggerAppCompatDialogFragment) =
        activity.supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()

    private fun removeAllFragment() =
        activity.supportFragmentManager.findFragmentById(R.id.content)
            ?.let {
                activity.supportFragmentManager.beginTransaction()
                    .remove(it)
                    .commit()
            }


  private  fun beginTransactionFragmentWithBackStack(fragment: DaggerFragment, container: Int) =
        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter, R.anim.fragment_exit,
                R.anim.fragment_pop_enter, R.anim.fragment_pop_exit
            )
            .replace(container, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()


    private fun beginTransactionActivity(mActivity: Class<*>, cycleFinish: Boolean) =
        Intent(activity, mActivity).let {
            activity.startActivity(it)
            if (cycleFinish) activity.finish()
        }

    private fun <T> beginTransactionActivity(mActivity: Class<*>, cycleFinish: Boolean, `object`: T?) =
        Intent(activity, mActivity).let {
            activity.startActivity(it)
            //    if (`object` != null) {
            //    if(object instanceof Data){
            //    intent.putExtra("data",(Data)object)
            //   }
            //  }
            if (cycleFinish) activity.finish()
        }

     private fun backPressedRouter() {
        val manager = activity.supportFragmentManager
        if (manager.backStackEntryCount == 1) {
        } else {
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    private  fun popBackStackRouter() {
        val fm: FragmentManager = activity.supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            if (i == 1) {
            } else {
                fm.popBackStack()
            }
        }
    }


     private fun restartAppBase() {
        val packageManager = activity.packageManager
        val intent = packageManager.getLaunchIntentForPackage(activity.packageName)
        if (intent != null) {
            val componentName = intent.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            activity.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }
    }

    fun content() : Int = R.id.content

    override fun finishActivity(){
        activity.finish()
    }

    override fun initDrawerLayout(drawerLayout: DrawerLayout) {
       this.mDrawerLayout = drawerLayout
    }

    override fun onBackPressed() {
        backPressedRouter()
    }

    override fun transaction(cmd: String, obj: Any) {
        when (cmd) {
            ConstRouter.NEWS_GROUP_FRAGMENT.route ->
              beginTransactionFragmentWithBackStack(NewsGroup.newInstance(), content())
            ConstRouter.NEWS_FRAGMENT.route ->
                if (obj is ArgObject) beginTransactionFragmentWithBackStack(News.newInstance(obj), content())
            ConstRouter.FAVORITES_NEWS_FRAGMENT.route ->
                beginTransactionFragmentWithBackStack(NewsFavorites.newInstance(),content())
            ConstRouter.SEARCH_FRAGMENT.route ->
                beginTransactionFragmentWithBackStack(Search.newInstance(),content())
            ConstRouter.WEB_FRAGMENT.route ->
                if (obj is NewsModel) beginTransactionFragmentWithBackStack(WebFragment.newInstance(obj),content())

        }
    }

    override fun transaction(cmd: String) {
        this.transaction(cmd,"")
    }

    override fun restartApp() {
       this.restartAppBase()
    }

    override fun removeAllFrag() {
        this.removeAllFragment()
    }

    override fun openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START)
    }

    override fun closeDrawer() {
        mDrawerLayout.closeDrawers()
    }

    override fun navDrawer(cmd: String) {
        closeDrawer()
        when (cmd) {
            ConstRouter.MAIN_DRAW.route -> transaction(ConstRouter.NEWS_GROUP_FRAGMENT.route)
            ConstRouter.FAVORITES_DRAW.route -> transaction(ConstRouter.FAVORITES_NEWS_FRAGMENT.route)
            ConstRouter.SEARCH_DRAW.route -> transaction(ConstRouter.SEARCH_FRAGMENT.route)
        }
    }

    override fun popBackStack() {
        closeDrawer()
        popBackStackRouter()
    }

}