package com.arch.test.util

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.arch.presentation.base.BaseFragment
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


inline fun <reified T : Fragment> FragmentManager.getFragment(args: Bundle? = null): Fragment {
    val clazz = T::class.java
    return findFragmentByTag(clazz.name) ?: return (clazz.getConstructor()
        .newInstance() as T).apply {
        args?.let {
            it.classLoader = javaClass.classLoader
            arguments = args
        }
    }
}



