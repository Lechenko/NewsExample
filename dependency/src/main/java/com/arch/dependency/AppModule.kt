package com.arch.dependency

import android.content.Context
import com.arch.comm.SharedPreferencesStorage
import com.arch.data.RepositoryApi
import com.arch.data.RepositoryDAO
import com.arch.data.RepositoryUtil
import com.arch.portdata.IRepositoryApi
import com.arch.portdata.IRepositoryDAO
import com.arch.portdata.IRepositoryUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
public object AppModule {

        @Singleton
        @Provides
        fun provideSharedPreferences(context: Context): SharedPreferencesStorage =
            SharedPreferencesStorage(context)

        @Singleton
        @Provides
        fun provideUtilRepository(context: Context,sharedPref: SharedPreferencesStorage
        ): IRepositoryUtil = RepositoryUtil(context,sharedPref)

        @Singleton
        @Provides
        fun provideRepositoryDAO(context: Context) : IRepositoryDAO =
            RepositoryDAO(context)

        @Singleton
        @Provides
        fun provideRepositoryApi() : IRepositoryApi = RepositoryApi()
}