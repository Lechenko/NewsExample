package com.arch.portdomain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel(
    val id: Long = 0,
    val name: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
) : StateEntity,Parcelable {
    @Suppress("UNCHECKED_CAST")
    override fun <NewsModel> getModel(): NewsModel = this as NewsModel


}