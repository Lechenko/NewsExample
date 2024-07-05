package com.arch.test

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.arch.data.RepositoryApi
import com.arch.data.RepositoryDAO
import com.arch.domain.news.NewsUseCase
import com.arch.portdata.IRepositoryApi
import com.arch.portdata.IRepositoryDAO
import com.arch.portdomain.model.StateLayer
import com.arch.portdomain.news.INewsUseCase
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import timber.log.Timber
import java.util.concurrent.TimeUnit


class TestNews{
   private var domain: INewsUseCase.UseCaseNews?=null

    companion object {
        var appContext: Context? = null
        @JvmStatic
        @BeforeClass
        fun stepUp() {
            Timber.plant(Timber.DebugTree())
            appContext =  InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as AppTest
        }

        @JvmStatic
        @AfterClass
        fun stepDown() {
            appContext = null

        }
    }

    @Before
    fun startTest() {
        val repositoryApi: IRepositoryApi = RepositoryApi()
        val repositoryDAO: IRepositoryDAO = appContext?.let {
            RepositoryDAO(context = it)
        } ?: error("context is Null")
        domain = NewsUseCase(repositoryApi, repositoryDAO)
    }

    @After
    fun stopTest() {

    }


    @Test
    fun testLoadNewsCountry() {
        domain?.let {
            it.loadNewsCountry("ru")
            it.byDomain()
                .test()
                .awaitDone(5,TimeUnit.SECONDS)
            .assertValue{model -> model.modelNews.size == 20}
            .assertValue{model -> model.status == 105}
        }
    }

    @Test
    fun testLoadNewsCountryEmpty() {
        domain?.let {
            it.loadNewsCountry("")
            it.byDomain()
                .test()
                .awaitDone(5,TimeUnit.SECONDS)
                .assertError{
                    it.message == "error HTTP 400 "
                }
        }
    }

    @Test
    fun testLoadNewsChannel() {
        domain?.let {dom ->
            val subscriber: TestObserver<StateLayer> = TestObserver.create()
            dom.loadNewsChannel("abc-news")
            dom.byDomain()
                .doOnNext { Timber.tag("TestNews")
                    .e("value status : " + it.status + " value size: " + it.modelNews.size) }
                .subscribe(subscriber)
            subscriber.awaitDone(5,TimeUnit.SECONDS)
            subscriber.assertValue{it.modelNews.size == 10}
            subscriber.assertValue{it.status == 105}
            subscriber.onComplete()
            subscriber.assertComplete().values()

        }
    }
}
