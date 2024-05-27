package com.arch.presentation.fragment.news


import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news.INewsUseCase
import com.arch.presentation.base.BaseVM
import com.arch.presentation.base.IState
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val router: IRouter,
    private val useCase: INewsUseCase.UseCaseNews
) : BaseVM(), IState {
   override fun state(): Observable<StateFlow> = Observable.defer {
        Observable.merge(
            publisherStateView(), useCase.stateDomain()
        )
    }.observeOn(provideSchedulersMain())


    fun init(arg: ArgObject) {
        when (arg.cmd) {
            ConstRouter.ARG_NEWS.route -> useCase.loadNewsChannel(arg.news)
            ConstRouter.ARG_COUNTRY.route -> useCase.loadNewsCountry(arg.country)
            ConstRouter.ARG_SEARCH.route -> useCase.loadNewsSearch(
                arg.news,
                arg.dateFrom,
                arg.dateTo
            )
        }
    }

    fun selectedNews(news: NewsModel) {
        router.transaction(ConstRouter.WEB_FRAGMENT.route, news)
    }

    fun saveNews(news: NewsModel) {
        useCase.saveNews(news)
    }

    fun shareContent(news: NewsModel) {
        news.url?.let {
            onNext(
                StateFlow(
                    EnumStateFlow.STATUS_LINK.const,
                    message = it
                )
            )
        }
    }

    fun menu() {
        router.openDrawer()
    }

    fun onDestroyView() {
        useCase.stopCase()
        disposePublisher()
    }
}