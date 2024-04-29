package com.arch.presentation.fragment.group

import com.arch.portdomain.StateFlowListener
import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsGroupModel
import com.arch.portdomain.model.StateFlow
import com.arch.portdomain.news_group.IGroupsUseCase
import com.arch.presentation.fragment.favorites.NewsFavoritesPresenter
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import com.arch.presentation.util.Language
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class NewsGroupPresenter @Inject constructor(
    val router: IRouter,
    private val useCase: IGroupsUseCase.UseCaseGroup,
    private val view: INewsGroup.View,
    private val stateFlow : StateFlowListener
) : INewsGroup.Presenter{
    private var disposable : CompositeDisposable? = null
    init {
        disposable = CompositeDisposable()
    }
    override fun init() {
        Language.flag = Language.ALL
        useCase.loadCategory()
    }

    override fun selectedItem(model: NewsGroupModel) {
        model.id?.let { router.transaction(ConstRouter.NEWS_FRAGMENT.route,
            ArgObject(ConstRouter.ARG_NEWS.route, news = it))}
    }

    override fun callMenu() {
        router.openDrawer()
    }

    override fun onClickLanguage(lang: String) {
        if (lang == Language.ALL) useCase.loadCategory()
        else useCase.selectLanguage(lang)
        view.updateAdapterLang()
    }

    override fun startView() {
        useCase.startCase()
        stateFlow.reset()
        val observable : Observable<StateFlow> = Observable.defer{stateFlow.observationState()}
        disposable?.add(observable.subscribe({
            when(it.status){
                EnumStateFlow.STATUS_OK_GROUP_LIST.const -> {
                    view.updateItemAdapter(it.modelGroup)
                }
                EnumStateFlow.STATUS_MGS.const -> {
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