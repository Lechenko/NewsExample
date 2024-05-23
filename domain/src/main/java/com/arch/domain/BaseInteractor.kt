package com.arch.domain

import com.arch.portdata.model.DataGroup
import com.arch.portdata.model.DataNews
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsGroupModel
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject


abstract class BaseInteractor  {
    private val publish : PublishSubject<StateFlow> = PublishSubject.create()
    private var jobThread: Scheduler = if (!BuildConfig.TEST_MODE_SCHEDULERS)Schedulers.io()
    else Schedulers.trampoline()
    protected open fun provideSchedulersIO() : Scheduler = jobThread
    fun stateOnNext(state : StateFlow) = publish.onNext(state)
    fun onError(state : StateFlow) = state.message.let {publish.onError(Throwable(it))}
    abstract fun stateDomain() : Observable<StateFlow>
    fun observationState(): Observable<StateFlow> =
        Observable.defer{publish}
            .subscribeOn(provideSchedulersIO())
            .filter{it.status != 0}
    fun reset() = publish.onNext(StateFlow(EnumStateFlow.RESET.const))

    fun mapperGroup(listGroup: List<DataGroup>): Single<List<NewsGroupModel>> =
        Single.just(listGroup)
            .flatMapObservable { Observable.fromIterable(it) }
            .map {
                NewsGroupModel(
                    it.id,
                    it.category,
                    it.country,
                    it.description,
                    it.language,
                    it.name,
                    it.url)
            }.toList()

    fun mapperNews(listNews: List<DataNews>): Single<List<NewsModel>> =
        Single.just(listNews)
            .flatMapObservable { Observable.fromIterable(it) }
            .map {
                NewsModel(
                    it.id,
                    it.name,
                    it.author,
                    it.title,
                    it.description,
                    it.url,
                    it.urlToImage,
                    it.publishedAt)
            }.toSortedList { p1, p2 -> (p1?.publishedAt ?: "").compareTo(p2?.publishedAt ?: "") }


    fun mapperNewsList(news : DataNews) : Single<NewsModel> = Single.just(news)
        .map {  NewsModel(
            it.id,
            it.name,
            it.author,
            it.title,
            it.description,
            it.url,
            it.urlToImage,
            it.publishedAt
        ) }

    fun mapperDataNews(news : NewsModel) : Single<DataNews> = Single.just(news)
        .map {
            val data = DataNews(
                it.name,
                it.author,
                it.title,
                it.description,
                it.url,
                it.urlToImage,
                it.publishedAt)
            data.id = it.id
            return@map data
        }
}