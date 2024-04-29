package com.arch.portdomain.favorites

import com.arch.portdomain.Interactor
import com.arch.portdomain.model.NewsModel

interface IFavoritesUseCase {
    interface UseCaseFavorites : Interactor{
        fun loadLocalNews()

        fun deleteFavoritesLocale(news : NewsModel)
    }
}