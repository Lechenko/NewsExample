package com.arch.news

import androidx.lifecycle.ViewModel
import com.arch.portdomain.ViewModelFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
@Suppress("UNCHECKED_CAST")
@Singleton
class NewsViewModelFactory @Inject constructor(private val provider: MutableMap<@JvmSuppressWildcards
Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelFactory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provider[modelClass]?.get() as T
    }
}