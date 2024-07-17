package com.arch.data

import android.content.Context
import com.arch.featurelocalstorage.Dao
import com.arch.featurelocalstorage.LocaleStorage
import com.arch.portdata.IRepositoryDAO
import com.arch.portdata.model.DataNews
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryDAO @Inject constructor(private val context: Context) : BaseDAO(),
    IRepositoryDAO {
    private val storage: Dao = LocaleStorage(context)
    private val gson: Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()

    override fun saveFavorites(data: DataNews): Completable =
        mapperToEntry(data)
            .doOnSuccess {
                Timber.tag(RepositoryDAO::class.simpleName.toString())
                    .e("saveFavorites %s", it)
            }
            .flatMapCompletable { storage.daoEntry().insertEntry(it) }
            .doOnError { Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it) }
            .doOnComplete {
                Timber.tag(RepositoryDAO::class.simpleName.toString())
                    .e("save name id %s", data.name)
            }

    override fun getFavorites(): Single<List<DataNews>> =
        storage.daoEntry().queryEntryList()
            .doOnSuccess {
                Timber.tag(RepositoryDAO::class.simpleName.toString())
                    .e("getFavorites %s", it)
            }
            .flatMap { mapperToData(it) }
            .doOnSuccess { Timber.tag("GSON").e(gson.toJson(it)) }
            .doOnError { Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it) }
            .doOnSuccess {
                Timber.tag(RepositoryDAO::class.simpleName.toString())
                    .e("getFavorites size  %s", it.size)
            }


    override fun deleteFavorites(data: DataNews): Single<Int> =
        mapperToEntry(data)
            .flatMap { Single.just(storage.daoEntry().deleteEntry(it)) }
            .doOnError { Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it) }
            .doOnSuccess {
                Timber.tag(RepositoryDAO::class.simpleName.toString())
                    .e("delete entry id %s", it)
            }

    override fun deleteFavoritesTable(): Completable = storage.daoEntry().deleteTable()
        .doOnError { Timber.tag(RepositoryDAO::class.simpleName.toString()).e(it) }


}