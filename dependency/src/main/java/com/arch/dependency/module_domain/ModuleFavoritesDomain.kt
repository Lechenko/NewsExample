package com.arch.dependency.module_domain

import com.arch.dependency.scope.FragmentScope
import com.arch.domain.favorites.FavoritesUseCase
import com.arch.portdomain.favorites.IFavoritesUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class ModuleFavoritesDomain {
    @FragmentScope
    @Binds
    abstract fun bindFavoritesUseCase(useCase: FavoritesUseCase) : IFavoritesUseCase.UseCaseFavorites
}