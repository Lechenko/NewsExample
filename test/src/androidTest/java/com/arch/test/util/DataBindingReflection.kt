package com.arch.test.util

import androidx.databinding.ViewDataBinding


class DataBindingReflection<T>(private val reflectClass : Class<T> ,
                               private val activity : Class<T>?) {
    fun getBinding() : ViewDataBinding {
        val bindingField = reflectClass.getDeclaredField("binding")
        bindingField.isAccessible = true
        return bindingField.get(activity) as ViewDataBinding
    }
}