package com.arch.news.module_activity


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arch.news.scope.ActivityScope
import com.arch.domain.main.MainUseCase
import com.arch.news.scope.ViewModelKey
import com.arch.portdomain.ViewModelFactory
import com.arch.portdomain.main.IMainUseCase
import com.arch.presentation.activity.MainActivity
import com.arch.presentation.activity.MainViewModel
import com.arch.presentation.router.IRouter
import com.arch.presentation.router.Router
import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ModuleMainActivity {
    //    @ActivityScope
//    @Binds
//    abstract fun bindsMainView(view : MainActivity) : IMainView.View
//    @ActivityScope
//    @Binds
//    abstract fun bindsMainPresenter(presenter : MainPresenter) : IMainView.Presenter
    companion object {
        @ActivityScope
        @Provides
        fun providesRouter(activity: MainActivity):
                IRouter = Router(view = activity, activity = activity)

        @ActivityScope
        @Provides
        fun providePermissions(activity: MainActivity): RxPermissions = RxPermissions(activity)

        @ActivityScope
        @Provides
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun bindMyViewModel(factory:  ViewModelFactory,activity : MainActivity): ViewModel =
            ViewModelProvider(activity, factory)[MainViewModel::class.java]


    }

    @ActivityScope
    @Binds
    abstract fun bindMainUseCase(useCase: MainUseCase): IMainUseCase


}