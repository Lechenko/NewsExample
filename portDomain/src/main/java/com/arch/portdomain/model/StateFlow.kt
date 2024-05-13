package com.arch.portdomain.model



data class StateFlow(
    val status: Int = 0,
    val message: String = "",
    val modelGroup: MutableList<NewsGroupModel> = mutableListOf(),
    val modelNews: MutableList<NewsModel> = mutableListOf()
)
enum class EnumStateFlow(val const : Int) {
    RESET(0),
    STATUS_OK(101),
    STATUS_OK_GROUP(102),
    STATUS_OK_GROUP_LIST(103),
    STATUS_OK_NEWS(104),
    STATUS_OK_NEWS_LIST(105),
    STATUS_MGS(201),
    STATUS_LINK(202),
    STATUS_EVENT(301)
}

enum class EventState(val const: String){
    UPDATE_ADAPTER("UPDATE_ADAPTER")
}