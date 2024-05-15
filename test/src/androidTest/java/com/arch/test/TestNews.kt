package com.arch.test

import android.annotation.SuppressLint
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
import io.reactivex.rxjava3.disposables.CompositeDisposable
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
        var disposable: CompositeDisposable? = null

        @JvmStatic
        @BeforeClass
        fun stepUp() {
            Timber.plant(Timber.DebugTree())
//            RxAndroidPlugins.setInitMainThreadSchedulerHandler {  Schedulers.trampoline() }
            appContext = InstrumentationRegistry.getInstrumentation().targetContext
            disposable = CompositeDisposable()
        }

        @JvmStatic
        @AfterClass
        fun stepDown() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
            disposable?.dispose()
            disposable = null
            appContext = null

        }
    }

    @Before
    fun startTest() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        val immediate = Schedulers.trampoline()
//        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
//        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
//        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
//        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
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
        disposable?.clear()
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
            subscriber.assertValue{it.modelNews.size > 0}
            subscriber.onComplete()
            subscriber.assertComplete()

        }
    }


//            val transaction = Completable.fromAction { domain?.loadNewsChannel("abc-news") }
//                .toSingle {}
//              return@defer Single.zip(
//                    state
//                    .doOnSuccess { Log.e("testLoadNewsChannel publish doOnSuccess ", it.toString()) }
//                    .doOnError {it.message?.let { msg -> Log.e("testLoadNewsChannel publish doOnError ", msg) }},
//                    transaction
//                        .doOnSuccess { Log.e("testLoadNewsChannel transaction doOnSuccess ", it.toString()) }
//                        .doOnError {it.message?.let { msg -> Log.e("testLoadNewsChannel transaction doOnError ", msg) }
//                        }
//                ) { list, _ -> list }.observeOn(Schedulers.trampoline())
//            }
//            .doOnSuccess { Log.e("testLoadNewsChannel zip doOnSuccess ", it.toString()) }
//            .doOnError { it.message?.let { msg -> Log.e("testLoadNewsChannel zip doOnError ", msg) } }
//            .test()

}
