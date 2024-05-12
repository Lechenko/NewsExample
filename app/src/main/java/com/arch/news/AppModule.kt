package com.arch.news

import android.content.Context
import com.arch.comm.SharedPreferencesStorage
import com.arch.data.RepositoryApi
import com.arch.data.RepositoryDAO
import com.arch.data.RepositoryUtil
import com.arch.portdata.IRepositoryApi
import com.arch.portdata.IRepositoryDAO
import com.arch.portdata.IRepositoryUtil
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

        @Singleton
        @Provides
        fun provideContext(app: App): Context = app.applicationContext

        @Singleton
        @Provides
        fun gsonApp() : Gson =
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setLenient()
                    .create()


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
        fun provideRepositoryDAO(context: Context,gson : Gson) : IRepositoryDAO =
            RepositoryDAO(context,gson)

        @Singleton
        @Provides
        fun provideRepositoryApi() : IRepositoryApi = RepositoryApi()
}