package com.arch.test

import android.content.Intent

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.arch.presentation.activity.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestUI {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        val intent =  Intent(Intent.ACTION_MAIN);
        activityRule.launchActivity(intent)

    }



    @Test
    fun testClickMenu() {
        onView(withId(com.arch.presentation.R.id.ivNewsCategoriesMenu)).perform(click())
            .inRoot(isPlatformPopup()).check(matches(isDisplayed()));
    }
}