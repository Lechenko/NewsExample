package com.arch.presentation.fragment.news

import com.arch.portdomain.StateFlowListener
import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news.INewsUseCase
import com.arch.presentation.fragment.favorites.NewsFavoritesPresenter
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class NewsPresenter @Inject constructor(
    private val view: INews.View,
    private val router: IRouter,
    private val useCase: INewsUseCase.UseCaseNews,
    private val stateFlow : StateFlowListener
) : INews.Presenter{
    private var disposable : CompositeDisposable? = null
    init {
        disposable = CompositeDisposable()
    }
    override fun init(arg: ArgObject) {
        when (arg.cmd) {
            ConstRouter.ARG_NEWS.route -> useCase.loadNewsChannel(arg.news)
            ConstRouter.ARG_COUNTRY.route -> useCase.loadNewsCountry(arg.country)
            ConstRouter.ARG_SEARCH.route -> useCase.loadNewsSearch(arg.news,arg.dateFrom,arg.dateTo)
        }
    }

    override fun selectedNews(news: NewsModel) {
        router.transaction(ConstRouter.WEB_FRAGMENT.route,news)
    }

    override fun saveNews(news: NewsModel) {
        useCase.saveNews(news)
    }

    override fun shareContent(news: NewsModel) {
       news.url?.let { view.shareLink(it)}
    }

    override fun menu() {
        router.openDrawer()
    }

    override fun startView() {
        useCase.startCase()
        stateFlow.reset()
        val observable : Observable<StateFlow> = Observable.defer{stateFlow.observationState()}
        disposable?.add(observable.subscribe({
            when(it.status){
                EnumStateFlow.STATUS_OK_NEWS_LIST.const -> {
                    view.updateAdapterList(it.modelNews)
                }
                EnumStateFlow.STATUS_MGS.const -> {
                    router.isProgress(false)
                    it.message.let {msg -> view.showMessage(msg)}
                }
            }
        },{
            view.showMessage(it.message.toString())
            Timber.tag(NewsFavoritesPresenter::class.java.name.toString())
                .i("error observationState : ".plus(it.message.toString()))

        },{
            Timber.tag(NewsFavoritesPresenter::class.java.name.toString())
                .i(" observationState dispose ")
        }))
    }

    override fun stopView() {

    }

    override fun pauseView() {

    }

    override fun destroyView() {
        useCase.stopCase()
    }
}