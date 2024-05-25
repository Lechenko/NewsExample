package com.arch.portdomain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsGroupModel(
    val id : String?,
    val category : String?,
    val country : String?,
    val description : String?,
    val language : String?,
    val name : String?,
    val url : String?) : StateEntity,Parcelable {
    @Suppress("UNCHECKED_CAST")
    override fun <NewsGroupModel> getModel(): NewsGroupModel = this as NewsGroupModel

}
