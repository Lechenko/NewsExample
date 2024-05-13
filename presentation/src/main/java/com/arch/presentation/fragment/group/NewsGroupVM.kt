package com.arch.presentation.fragment.group

import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.EventState
import com.arch.portdomain.model.NewsGroupModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news_group.IGroupsUseCase
import com.arch.presentation.base.BaseVM
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import com.arch.presentation.util.Language
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsGroupVM @Inject constructor(
    private val router: IRouter,
    private val useCase: IGroupsUseCase.UseCaseGroup
) : BaseVM() {
    fun state(): Observable<StateFlow> = Observable.defer {
        Observable.merge(
            publisherStateView(), useCase.stateDomain()
        )
    }.compose(applyObservableSchedulers())

    init {
        useCase.startCase()
    }

    fun init() {
        Language.flag = Language.ALL
        useCase.loadCategory()
    }

    fun selectedItem(model: NewsGroupModel) {
        model.id?.let {
            router.transaction(
                ConstRouter.NEWS_FRAGMENT.route,
                ArgObject(ConstRouter.ARG_NEWS.route, news = it)
            )
        }
    }

    fun callMenu() {
        router.openDrawer()
    }

    fun onClickLanguage(lang: String) {
        if (lang == Language.ALL) useCase.loadCategory()
        else useCase.selectLanguage(lang)
        onNext(
            StateFlow(
                EnumStateFlow.STATUS_EVENT.const,
                message = EventState.UPDATE_ADAPTER.const
            )
        )
    }

    override fun onCleared() {
        router.onStopView()
        useCase.stopCase()
        super.onCleared()
    }

    fun onDestroyView() = useCase.stopCase()
}