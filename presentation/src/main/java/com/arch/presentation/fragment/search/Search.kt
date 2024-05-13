package com.arch.presentation.fragment.search

import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentSearchBinding


class Search : BaseFragment<FragmentSearchBinding,SearchVM>(){

    companion object {
        @JvmStatic
        fun newInstance() = Search()
    }

    override val layoutRes: Int = R.layout.fragment_search

    override fun initFragmentView() {
       binding?.let {
           it.event = viewModel

       }
    }

    override fun attachFragment() {

    }

    override fun startFragment() {

    }

    override fun stopFragment() {

    }

    override fun destroyFragment() {

    }

    override fun pauseFragment() {

    }

    override fun resume() {

    }
}