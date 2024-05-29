package com.arch.domain.news_group

import com.arch.comm.ErrorType
import com.arch.domain.BaseInteractor
import com.arch.portdata.IRepositoryApi
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news_group.IGroupsUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class GroupUseCase @Inject constructor(private val repositoryApi: IRepositoryApi) :
    BaseInteractor(),
    IGroupsUseCase.UseCaseGroup {

    private var disposable : CompositeDisposable ?= null
    init {
        disposable = CompositeDisposable()
    }

    override fun loadCategory() {
        disposable?.add(Single.defer { repositoryApi.loadCategory() }
            .subscribeOn(provideSchedulersIO())
            .flatMap { mapperGroup(it) }
            .subscribe({
                Timber.tag(GroupUseCase::class.java.name.toString())
                    .i("list size newModule".plus(it.size))
                onSuccess(
                    StateFlow(
                        status = EnumStateFlow.STATUS_OK_GROUP_LIST.const,
                        modelGroup = it.toMutableList())
                )
            },{
                Timber.tag(GroupUseCase::class.java.name.toString())
                    .i("error loadLocalNews ".plus(it.message.toString()))
                onError(
                    StateFlow(
                        status = EnumStateFlow.STATUS_MGS.const,
                        message = ErrorType.ERROR.type.plus(" ")
                            .plus(it.message))
                )
            }))
    }

    override fun selectLanguage(language: String) {
        disposable?.add(Single.defer { repositoryApi.newsLanguage(language) }
            .subscribeOn(provideSchedulersIO())
            .flatMap { mapperGroup(it) }
            .doOnSuccess{
             }
            .doOnError {

            }
            .subscribe({
                Timber.tag(GroupUseCase::class.java.name.toString())
                    .i("list size newModule".plus(it.size))
                onSuccess(
                    StateFlow(
                        status = EnumStateFlow.STATUS_OK_GROUP_LIST.const,
                        modelGroup = it.toMutableList())
                )
            },{
                Timber.tag(GroupUseCase::class.java.name.toString())
                    .i("error loadLocalNews ".plus(it.message.toString()))
                onError(
                    StateFlow(
                        status = EnumStateFlow.STATUS_MGS.const,
                        message = ErrorType.ERROR.type.plus(" ")
                            .plus(it.message))
                )
            }))
    }

    override fun startCase() {
        //stateFlow.reset()
    }

    override fun stopCase() {
       disposable?.dispose()
       disposable = null
    }

    override fun byDomain(): Observable<StateFlow> = observationState()
}