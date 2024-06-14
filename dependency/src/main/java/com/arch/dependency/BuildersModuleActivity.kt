package com.arch.dependency


import com.arch.dependency.module_activity.ModuleMainActivity
import com.arch.dependency.scope.ActivityScope
import com.arch.presentation.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class BuildersModuleActivity {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ModuleMainActivity::class, BuildersModuleFragment::class])
    public  abstract fun bindMainActivity(): MainActivity
}