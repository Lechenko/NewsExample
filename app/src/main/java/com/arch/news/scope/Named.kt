package com.arch.news.scope

import javax.inject.Qualifier
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Named(
    /** The name.  */
    val value: String = ""
)