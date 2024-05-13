package com.arch.domain.favorites

import com.arch.comm.ErrorType
import com.arch.domain.BaseInteractor
import com.arch.portdata.IRepositoryDAO
import com.arch.portdomain.favorites.IFavoritesUseCase
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.Collections
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(private val repositoryDao : IRepositoryDAO)
    : BaseInteractor(),IFavoritesUseCase.UseCaseFavorites {
    private val disposable = CompositeDisposable()

    override fun loadLocalNews() {
       disposable.add(Single.defer { repositoryDao.getFavorites() }
           .subscribeOn(Schedulers.io())
           .flatMap { mapperNews(it)}
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
               Timber.tag(FavoritesUseCase::class.java.name.toString())
                   .i("list size newModule".plus(it.size))
           },{
               Timber.tag(FavoritesUseCase::class.java.name.toString())
                   .i("error loadLocalNews ".plus(it.message.toString()))
           }))
    }

    override fun deleteFavoritesLocale(news: NewsModel) {
        disposable.add(Single.defer { mapperDataNews(news) }
            .subscribeOn(Schedulers.io())
            .flatMap{ repositoryDao.deleteFavorites(it) }
            .subscribe({
                onNext(StateFlow(
                    status = EnumStateFlow.STATUS_OK_NEWS.const,
                    modelNews = Collections.singletonList(news)
                ))
            },{
                onNext(StateFlow(
                    status = EnumStateFlow.STATUS_MGS.const,
                    message = ErrorType.ERROR.type.plus(" ")
                        .plus(it.message)))
            }))
    }

    override fun startCase() {

    }

    override fun stopCase() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun stateDomain(): Observable<StateFlow> = observationState()


}