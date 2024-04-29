package com.arch.data

import android.content.Context
import com.arch.featurelocalstorage.Dao
import com.arch.featurelocalstorage.LocaleStorage
import com.arch.portdata.IRepositoryDAO
import com.arch.portdata.model.DataNews
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import javax.inject.Inject

class RepositoryDAO @Inject constructor(context : Context,val gson: Gson) :BaseDAO(),IRepositoryDAO {
    private val storage : Dao

    init {
        storage = LocaleStorage(context)
    }

    override fun saveFavorites(data: DataNews): Completable  =
        mapperToEntry(data)
            .doOnSuccess { Timber.tag(RepositoryDAO::class.simpleName.toString())
                .e("saveFavorites %s",it)}
            .flatMapCompletable {storage.daoEntry().insertEntry(it)}
            .doOnError{Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it)}
            .doOnComplete {Timber.tag(RepositoryDAO::class.simpleName.toString())
                .e("save entry id %s",data.id)}

    override fun getFavorites(): Single<List<DataNews>> =
        storage.daoEntry().queryEntryList()
            .doOnSuccess { Timber.tag(RepositoryDAO::class.simpleName.toString())
                .e("getFavorites %s",it)}
            .flatMap{mapperToData(it)}
            .doOnSuccess { Timber.tag("GSON").e(gson.toJson(it)) }
            .doOnError{Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it)}
            .doOnSuccess {Timber.tag(RepositoryDAO::class.simpleName.toString())
                .e("getFavorites size  %s",it.size) }


    override fun deleteFavorites(data: DataNews): Single<Int>  =
        mapperToEntry(data)
            .flatMap{Single.just(storage.daoEntry().deleteEntry(it))}
            .doOnError{Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it)}
            .doOnSuccess {Timber.tag(RepositoryDAO::class.simpleName.toString())
                .e("delete entry id %s",it)}

    override fun deleteFavoritesTable(): Completable = storage.daoEntry().deleteTable()
        .doOnError{Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it)}


}