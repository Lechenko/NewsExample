package com.arch.data

import com.arch.featureremoteapi.IRemoteStorage
import com.arch.featureremoteapi.RemoteStorage
import com.arch.portdata.IRepositoryApi
import com.arch.portdata.model.DataGroup
import com.arch.portdata.model.DataNews
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.util.Arrays

class RepositoryApi : BaseRepApi(), IRepositoryApi {
    private val remoteStorage : IRemoteStorage   = RemoteStorage()
    override fun newsLanguage(language: String): Single<List<DataGroup>> =
        remoteStorage.newsLanguage(language)
            .flatMap {mapperFromGroup(it)}
            .doOnSuccess { Timber.tag(RepositoryApi::class.simpleName.toString())
                .e("newsLanguage %s",Arrays.asList(it))}
            .doOnError{Timber.tag(RepositoryApi::class.simpleName.toString()).e(it)}

    override fun newsSearch(search: String, dataStart: String, dataEnd: String):
            Single<List<DataNews>> = remoteStorage.newsSearch(search,dataStart,dataEnd)
        .flatMap {mapperFromNews(it)}
        .doOnSuccess { Timber.tag(RepositoryApi::class.simpleName.toString())
            .e("newsSearch %s", Arrays.asList(it))}
        .doOnError{Timber.tag(RepositoryApi::class.simpleName.toString()).e(it)}

    override fun loadCategory(): Single<List<DataGroup>> = remoteStorage.loadCategory()
        .flatMap {mapperFromGroup(it)}
        .doOnSuccess{Timber.tag(RepositoryApi::class.java.name.toString())
                .i("loadCategory %s", Arrays.asList(it))}
        .doOnError{Timber.tag(RepositoryApi::class.simpleName.toString()).e(it)}

    override fun newsChannel(newsChannel: String): Single<List<DataNews>> =
        remoteStorage.newsChannel(newsChannel)
        .flatMap {mapperFromNews(it)}
            .doOnSuccess{Timber.tag(RepositoryApi::class.java.name.toString())
                .i("newsChannel %s", Arrays.asList(it))}
        .doOnError{Timber.tag(RepositoryApi::class.simpleName.toString()).e(it)}

    override fun newsCountry(newsCountry: String): Single<List<DataNews>> =
        remoteStorage.newsCountry(newsCountry)
            .flatMap {mapperFromNews(it)}
            .doOnSuccess{Timber.tag(RepositoryApi::class.java.name.toString())
                .i("newsCountry %s", Arrays.asList(it))}
            .doOnError{Timber.tag(RepositoryApi::class.simpleName.toString()).e(it)}
}