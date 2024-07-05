package com.arch.presentation.fragment.favorites

import com.arch.portdomain.favorites.IFavoritesUseCase
import com.arch.portdomain.model.EnumStateLayer
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateLayer
import com.arch.presentation.base.BaseVM
import com.arch.presentation.base.IState
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NewsFavoritesVM @Inject constructor(
    private val router: IRouter,
    private val useCase: IFavoritesUseCase.UseCaseFavorites
) : BaseVM(), IState {
    override fun state(): Observable<StateLayer> = Observable.defer {
        Observable.merge(publisherStateView(), useCase.byDomain())
    }.observeOn(provideSchedulersMain())
        .map { state ->
            if (EnumStateLayer.STATUS_OK_NEWS.const == state.status ||
                EnumStateLayer.STATUS_MGS.const == state.status) {
                router.isProgress(false)
            }
            state
        }

    init {
        useCase.startCase()
    }

    fun init() = useCase.loadLocalNews()
    fun shareLink(item: NewsModel) {
        item.url?.let {
            onNext(
                StateLayer(
                    EnumStateLayer.STATUS_LINK.const,
                    message = it
                )
            )
        }
    }

    fun deleteNewsLocale(news: NewsModel) =
        router.isProgress(true).also { useCase.deleteFavoritesLocale(news) }

    fun selectedNews(item: NewsModel) =
        router.transaction(ConstRouter.WEB_FRAGMENT.route, item)

    fun menu() = router.openDrawer()

    fun onDestroyView() {
        useCase.stopCase()
        disposePublisher()
    }
}