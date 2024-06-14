package com.arch.dependency


import com.arch.dependency.module_fragment.ModuleFavorites
import com.arch.dependency.module_fragment.ModuleNews
import com.arch.dependency.module_fragment.ModuleNewsGroup
import com.arch.dependency.module_fragment.ModuleSearch
import com.arch.dependency.module_fragment.ModuleWeb
import com.arch.dependency.scope.FragmentScope
import com.arch.presentation.fragment.favorites.NewsFavorites
import com.arch.presentation.fragment.group.NewsGroup
import com.arch.presentation.fragment.news.News
import com.arch.presentation.fragment.search.Search
import com.arch.presentation.fragment.web.WebFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
public  abstract class BuildersModuleFragment {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ModuleNewsGroup::class])
    public  abstract fun bindNewsGroupFragment() : NewsGroup

    @FragmentScope
    @ContributesAndroidInjector(modules = [ModuleNews::class])
    public  abstract fun bindNewsFragment() : News

    @FragmentScope
    @ContributesAndroidInjector(modules = [ModuleFavorites::class])
    public  abstract fun bindFavoritesFragment() : NewsFavorites

    @FragmentScope
    @ContributesAndroidInjector(modules = [ModuleSearch::class])
    public  abstract fun bindSearchFragment() : Search

    @FragmentScope
    @ContributesAndroidInjector(modules = [ModuleWeb::class])
    public  abstract fun bindWebFragment() : WebFragment

}