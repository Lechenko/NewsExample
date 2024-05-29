package com.arch.presentation.fragment.group

import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.EventState
import com.arch.portdomain.model.NewsGroupModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news_group.IGroupsUseCase
import com.arch.presentation.base.BaseVM
import com.arch.presentation.base.IState
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import com.arch.presentation.util.Language
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NewsGroupVM @Inject constructor(
    private val router: IRouter,
    private val useCase: IGroupsUseCase.UseCaseGroup
) : BaseVM(), IState {
    override fun state(): Observable<StateFlow> = Observable.defer {
        Observable.merge(
            publisherStateView(), useCase.byDomain()
        )
    }.observeOn(provideSchedulersMain())

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

    fun onDestroyView() {
        useCase.stopCase()
        disposePublisher()
    }
}