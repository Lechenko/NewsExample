package com.arch.presentation.fragment.favorites

import com.arch.portdomain.StateFlowListener
import com.arch.portdomain.favorites.IFavoritesUseCase
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class NewsFavoritesPresenter @Inject constructor(private val view : IFavoritesNews.View,
    private val router : IRouter,private val useCase: IFavoritesUseCase.UseCaseFavorites,
    private val stateFlow : StateFlowListener)
    : IFavoritesNews.Presenter{
    private var disposable : CompositeDisposable ? = null
    override fun init() = useCase.loadLocalNews()
    override fun shareLink(item: NewsModel) {
        item.url?.let { uri -> view.shareLink(uri) }
    }
    override fun deleteNewsLocale(news: NewsModel) =
            router.isProgress(true).also {  useCase.deleteFavoritesLocale(news)  }
    override fun selectedNews(item: NewsModel) =
        router.transaction(ConstRouter.WEB_FRAGMENT.route,item)

    override fun menu() = router.openDrawer()

    override fun startView() {
        useCase.startCase()
        disposable = CompositeDisposable()
        stateFlow.reset()
        val observable : Observable<StateFlow> = Observable.defer{stateFlow.observationState()}
        disposable?.add(observable.subscribe({
            when(it.status){
                EnumStateFlow.STATUS_OK_NEWS.const -> {
                    router.isProgress(false)
                    view.deleteItemAdapter(it.modelNews[0])
                    view.showMessage("delete ok")
                }
                EnumStateFlow.STATUS_OK_NEWS_LIST.const -> {
                    view.updateListAdapter(it.modelNews)
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
        disposable?.clear()
    }

    override fun pauseView() {

    }

    override fun destroyView() {
        useCase.stopCase()
        stateFlow.reset()
        disposable?.dispose()
    }
}