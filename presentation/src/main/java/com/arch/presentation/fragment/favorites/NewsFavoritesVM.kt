package com.arch.presentation.fragment.favorites

import com.arch.portdomain.favorites.IFavoritesUseCase
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.presentation.base.BaseVM
import com.arch.presentation.router.ConstRouter
import com.arch.presentation.router.IRouter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NewsFavoritesVM @Inject constructor(
    private val router: IRouter,
    private val useCase: IFavoritesUseCase.UseCaseFavorites
) : BaseVM() {
    fun state(): Observable<StateFlow> = Observable.defer {
        Observable.merge(
            publisherStateView(), useCase.stateDomain()
        ).map { state ->
            if (
                EnumStateFlow.STATUS_OK_NEWS.const == state.status ||
                EnumStateFlow.STATUS_MGS.const == state.status) {
                router.isProgress(false)
            }
            state
        }
    }.observeOn(AndroidSchedulers.mainThread())

    init {
        useCase.startCase()
    }

    fun init() = useCase.loadLocalNews()
    fun shareLink(item: NewsModel) {
        item.url?.let {
            onNext(
                StateFlow(
                    EnumStateFlow.STATUS_LINK.const,
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

    override fun onCleared() {
        router.onStopView()
        useCase.stopCase()
        super.onCleared()
    }

    fun onDestroyView() = useCase.stopCase()
}