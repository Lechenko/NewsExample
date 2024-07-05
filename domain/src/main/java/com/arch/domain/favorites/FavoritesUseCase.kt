package com.arch.domain.favorites

import com.arch.comm.ErrorType
import com.arch.domain.BaseInteractor
import com.arch.portdata.IRepositoryDAO
import com.arch.portdomain.favorites.IFavoritesUseCase
import com.arch.portdomain.model.EnumStateLayer
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateLayer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import java.util.Collections
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(private val repositoryDao : IRepositoryDAO)
    : BaseInteractor(),IFavoritesUseCase.UseCaseFavorites {
    private val disposable = CompositeDisposable()

    override fun loadLocalNews() {
       disposable.add(Single.defer { repositoryDao.getFavorites() }
           .subscribeOn(provideSchedulersIO())
           .flatMap { mapperNews(it)}
           .subscribe({
               Timber.tag(FavoritesUseCase::class.java.name.toString())
                   .i("list size newModule".plus(it.size))
               onSuccess(StateLayer(
                   status = EnumStateLayer.STATUS_OK_NEWS_LIST.const,
                   modelNews = it.toMutableList()))
           },{
               Timber.tag(FavoritesUseCase::class.java.name.toString())
                   .i("error loadLocalNews ".plus(it.message.toString()))
               onError(StateLayer(
                   status = EnumStateLayer.STATUS_MGS.const,
                   message = ErrorType.ERROR.type.plus(" ")
                       .plus(it.message)))
           }))
    }

    override fun deleteFavoritesLocale(news: NewsModel) {
        disposable.add(Single.defer { mapperDataNews(news) }
            .subscribeOn(provideSchedulersIO())
            .flatMap{ repositoryDao.deleteFavorites(it) }
            .subscribe({
                onSuccess(StateLayer(
                    status = EnumStateLayer.STATUS_OK_NEWS.const,
                    modelNews = Collections.singletonList(news)
                ))
            },{
                onError(StateLayer(
                    status = EnumStateLayer.STATUS_MGS.const,
                    message = ErrorType.ERROR.type.plus(" ")
                        .plus(it.message)))
            }))
    }

    override fun startCase() {

    }

    override fun stopCase() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun byDomain(): Observable<StateLayer> = observationState()


}