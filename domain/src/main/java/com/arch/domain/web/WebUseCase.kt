package com.arch.domain.web

import com.arch.comm.ErrorType
import com.arch.domain.BaseInteractor
import com.arch.portdata.IRepositoryDAO
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.web.IWebUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class WebUseCase @Inject constructor(
    private val repositoryDao: IRepositoryDAO
) : BaseInteractor(), IWebUseCase.UseCaseWeb {
    private var disposable: CompositeDisposable? = CompositeDisposable()


    override fun saveNews(news: NewsModel) {
        disposable?.add(Single.defer { mapperDataNews(news) }
            .subscribeOn(provideSchedulersIO())
            .flatMapCompletable { repositoryDao.saveFavorites(it) }
            .subscribe({
                stateFlow(
                    StateFlow(
                        status = EnumStateFlow.STATUS_MGS.const,
                        message = ErrorType.ERROR.type.plus(" ")
                            .plus("save ok")
                    )
                )
            }, {
                Timber.tag(WebUseCase::class.simpleName.toString()).e(it)
                stateFlow(
                    StateFlow(
                        status = EnumStateFlow.STATUS_MGS.const,
                        message = ErrorType.ERROR.type.plus(" ")
                            .plus(it.message)
                    )
                )
            }))
    }

    override fun startCase() {

    }

    override fun stopCase() {
        disposable?.dispose()
        disposable = null
    }

    override fun stateDomain(): Observable<StateFlow> = observationState()
}