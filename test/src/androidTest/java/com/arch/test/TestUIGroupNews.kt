package com.arch.test


import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import org.hamcrest.Matcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arch.presentation.R.*
import com.arch.presentation.activity.MainActivity
import com.arch.test.util.*
import com.squareup.rx3.idler.Rx3Idler

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import com.arch.test.util.RecyclerViewMatcher.Companion.recyclerViewWithId
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestUIGroupNews {
    private val recyclerViewMatcher: RecyclerViewMatcher = recyclerViewWithId(id.rvNewsCategoriesDisplay)
    private val recyclerView: Matcher<View> = withId(id.rvNewsCategoriesDisplay)
    private lateinit var activity: MainActivity

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            activity = it
        }
       RxJavaPlugins.setInitComputationSchedulerHandler(
            Rx3Idler.create("RxJava 3.x Computation Scheduler"));
        RxJavaPlugins.setInitIoSchedulerHandler(
            Rx3Idler.create("RxJava 3.x IO Scheduler"))
    }

    @After
    fun downUp() {
     //   IdlingRegistry.getInstance().unregister(signInIdlingResource)
    }

    @Test
    fun isDisplayRecyclerView() {
        onView(recyclerView)
        .check(matches(isDisplayed()))
    }


    @Test
    fun testRv_position_0_withText(){
        onView(recyclerViewMatcher
            .viewHolderViewAtPosition(0, id.contentItem))
            .check(matches(isDisplayed()))
            .check(matches(withText("ABS News")))
            .perform(click())
    }

    @Test
    fun testRv_scroll_position_20(){
        onView(recyclerViewMatcher
            .viewHolderViewAtPosition(20, id.contentItem))
            .check(matches(isDisplayed()))
            .perform( RVScroll())

    }
}



//@Test
//fun isItemABSNewsNewsGroup() {
//        val schedulerIo = Schedulers.io()
//        val schedulerMain = AndroidSchedulers.mainThread()
//        val wrappedMain = Rx3Idler.wrap(schedulerMain, "Main")
//        val wrappedIo = Rx3Idler.wrap(schedulerIo, "IO")
//        IdlingRegistry.getInstance().register(wrappedMain)
//        IdlingRegistry.getInstance().register(wrappedIo)
//    onView(recyclerView)
//        .check(matches(isDisplayed()))
//        .check(matches(withText("ABS News")))
//        IdlingRegistry.getInstance().unregister(wrappedMain)
//        IdlingRegistry.getInstance().unregister(wrappedIo)
//
//}
