package com.arch.news.module_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arch.domain.favorites.FavoritesUseCase
import com.arch.news.scope.FragmentScope
import com.arch.news.scope.ViewModelKey
import com.arch.portdomain.ViewModelFactory
import com.arch.portdomain.favorites.IFavoritesUseCase
import com.arch.presentation.fragment.favorites.NewsFavoritesVM
import com.arch.presentation.fragment.news.News
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleFavorites {


    @FragmentScope
    @Binds
    abstract fun bindFavoritesUseCase(useCase: FavoritesUseCase) : IFavoritesUseCase.UseCaseFavorites


    companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(NewsFavoritesVM::class)
        fun bindMyViewModel(factory: ViewModelFactory, fragment: News): ViewModel =
            ViewModelProvider(fragment, factory)[NewsFavoritesVM::class.java]
    }


}