package com.arch.domain

import com.arch.portdata.model.DataGroup
import com.arch.portdata.model.DataNews
import com.arch.portdomain.model.EnumStateLayer
import com.arch.portdomain.model.NewsGroupModel
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateLayer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject


abstract class BaseInteractor  {
    private val publish : PublishSubject<StateLayer> = PublishSubject.create()
    private var jobThread: Scheduler = Schedulers.io()
    protected open fun provideSchedulersIO() : Scheduler = jobThread
    fun onSuccess(state : StateLayer) = publish.onNext(state)
    fun onError(state : StateLayer) = state.message.let {publish.onError(Throwable(it))}
    abstract fun byDomain() : Observable<StateLayer>
    fun observationState(): Observable<StateLayer> =
        Observable.defer{publish}
            .subscribeOn(provideSchedulersIO())
            .filter{it.status != 0}
    fun reset() = publish.onNext(StateLayer(EnumStateLayer.RESET.const))

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