package com.arch.dependency.module_fragment

import androidx.lifecycle.ViewModelProvider
import com.arch.dependency.scope.FragmentScope
import com.arch.dependency.scope.ViewModelKey
import com.arch.presentation.base.ViewModelFactory
import com.arch.presentation.fragment.favorites.NewsFavoritesVM
import com.arch.presentation.fragment.news.News
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleFavorites {

    public companion object {
        @FragmentScope
        @Provides
        @IntoMap
        @ViewModelKey(NewsFavoritesVM::class)
        fun bindFavoritesViewModel(factory: ViewModelFactory, fragment: News): NewsFavoritesVM =
            ViewModelProvider(fragment, factory)[NewsFavoritesVM::class.java]
    }


}