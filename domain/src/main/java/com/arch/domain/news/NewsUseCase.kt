package com.arch.domain.news

import com.arch.comm.ErrorType
import com.arch.domain.BaseInteractor
import com.arch.portdata.IRepositoryApi
import com.arch.portdata.IRepositoryDAO
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news.INewsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repositoryApi: IRepositoryApi,
    private val repositoryDao: IRepositoryDAO
) : BaseInteractor(), INewsUseCase.UseCaseNews {
    private val disposable = CompositeDisposable()

    override fun loadNewsChannel(newsChannel: String) {
        disposable.add(Single.defer { repositoryApi.newsChannel(newsChannel) }
            .subscribeOn(Schedulers.io())
            .flatMap { mapperNews(it) }
            .doOnSuccess{ onNext(
                StateFlow(
                status = EnumStateFlow.STATUS_OK_NEWS_LIST.const,
                modelNews = it.toMutableList())
            ) }
            .doOnError {
                onNext(
                    StateFlow(
                    status = EnumStateFlow.STATUS_MGS.const,
                    message = ErrorType.ERROR.type.plus(" ")
                        .plus(it.message))
                )
            }
            .subscribe({
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("list size newModule".plus(it.size))
            },{
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("error loadLocalNews ".plus(it.message.toString()))
            }))
    }

    override fun loadNewsCountry(country: String) {
        disposable.add(Single.defer { repositoryApi.newsCountry(country)}
            .subscribeOn(Schedulers.io())
            .flatMap { mapperNews(it) }
            .doOnSuccess{onNext(StateFlow(
                status = EnumStateFlow.STATUS_OK_NEWS_LIST.const,
                modelNews = it.toMutableList())) }
            .doOnError {
               onNext(StateFlow(
                    status = EnumStateFlow.STATUS_MGS.const,
                    message = ErrorType.ERROR.type.plus(" ")
                        .plus(it.message)))
            }
            .subscribe({
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("list size newModule".plus(it.size))
            },{
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("error loadLocalNews ".plus(it.message.toString()))
            }))
    }

    override fun loadNewsSearch(newSearch: String, dateFrom: String, dateTo: String) {
        disposable.add(Single.defer { repositoryApi.newsSearch(newSearch,dateFrom,dateTo)}
            .subscribeOn(Schedulers.io())
            .flatMap { mapperNews(it) }
            .doOnSuccess{ onNext(StateFlow(
                status = EnumStateFlow.STATUS_OK_NEWS_LIST.const,
                modelNews = it.toMutableList())) }
            .doOnError {
                onNext(StateFlow(
                    status = EnumStateFlow.STATUS_MGS.const,
                    message = ErrorType.ERROR.type.plus(" ")
                        .plus(it.message)))
            }
            .subscribe({
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("list size newModule".plus(it.size))
            },{
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("error loadLocalNews ".plus(it.message.toString()))
            }))
    }

    override fun saveNews(news: NewsModel) {
        disposable.add(Single.defer{mapperDataNews(news)}
            .subscribeOn(Schedulers.io())
            .flatMapCompletable { repositoryDao.saveFavorites(it) }
            .doOnEvent{ onNext(StateFlow(
                status = EnumStateFlow.STATUS_OK_NEWS_LIST.const,
                message = "save ok")) }
            .doOnError {
               onNext(StateFlow(
                    status = EnumStateFlow.STATUS_MGS.const,
                    message = ErrorType.ERROR.type.plus(" ")
                        .plus(it.message)))
            }
            .subscribe({
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("save ok")
            },{
                Timber.tag(NewsUseCase::class.java.name.toString())
                    .i("error loadLocalNews ".plus(it.message.toString()))
            }))

    }

    override fun startCase() {

    }

    override fun stopCase() {
         if (!disposable.isDisposed) disposable.dispose()
    }

    override fun stateDomain(): Observable<StateFlow> = observationState()

}