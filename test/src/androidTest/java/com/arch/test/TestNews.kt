package com.arch.test

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.arch.data.RepositoryApi
import com.arch.data.RepositoryDAO
import com.arch.domain.news.NewsUseCase
import com.arch.portdata.IRepositoryApi
import com.arch.portdata.IRepositoryDAO
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news.INewsUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import timber.log.Timber
import java.util.concurrent.TimeUnit


class TestNews {
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private var domain: INewsUseCase.UseCaseNews? = null


    companion object {
        var appContext: Context? = null
        @JvmStatic
        @BeforeClass
        fun stepUp() {
            Timber.plant(Timber.DebugTree())
            appContext = InstrumentationRegistry.getInstrumentation().targetContext
        }

        @JvmStatic
        @AfterClass
        fun stepDown() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
            appContext = null

        }
    }

    @Before
    fun startTest() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        val immediate = Schedulers.trampoline()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        val repositoryApi: IRepositoryApi = RepositoryApi()
        val repositoryDAO: IRepositoryDAO = appContext?.let {
            RepositoryDAO(
                context = it,
                gson = DependencyTest.gsonApp()
            )
        } ?: error("context is Null")

        domain = NewsUseCase(repositoryApi, repositoryDAO)
    }

    @After
    fun stopTest() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
    @Test
    fun testLoadGroupNews() {
        domain?.let {dom ->
            val subscriber: TestObserver<StateFlow> = TestObserver.create()
            dom.loadNewsChannel("abc-news")
            dom.stateDomain()
                .observeOn(Schedulers.trampoline())
                .doOnNext { Timber.tag("TestNews")
                    .e("value status : " + it.status + " value size: " + it.modelNews.size) }
                .subscribe(subscriber)
            subscriber.awaitDone(5,TimeUnit.SECONDS)
            subscriber.assertValue{it.modelNews.size == 10}
            subscriber.assertValue{it.status == 105}
            subscriber.onComplete()
            subscriber.assertComplete()

        }
    }

    @Test
    fun testLoadNewsChannel() {
        domain?.let {dom ->
            val subscriber: TestObserver<StateFlow> = TestObserver.create()
            dom.loadNewsChannel("abc-news")
            dom.stateDomain()
                .observeOn(Schedulers.trampoline())
                .doOnNext { Timber.tag("TestNews")
                    .e("value status : " + it.status + " value size: " + it.modelNews.size) }
                .subscribe(subscriber)
            subscriber.awaitDone(5,TimeUnit.SECONDS)
            subscriber.assertValue{it.modelNews.size == 10}
            subscriber.assertValue{it.status == 105}
            subscriber.onComplete()
            subscriber.assertComplete()

        }
    }
}
