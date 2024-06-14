package com.arch.dependency

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.arch.presentation.base.ViewModelFactory
import org.jetbrains.annotations.NotNull
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class NewsViewModelFactory @Inject constructor(
    @SuppressLint("KotlinNullnessAnnotation") @NotNull private val provider: MutableMap<@JvmSuppressWildcards
Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provider[modelClass]?.get() as T
    }



}