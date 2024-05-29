package com.arch.presentation.fragment.news

import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.StateFlow
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.base.IState
import com.arch.presentation.databinding.FragmentNewsBinding
import com.arch.presentation.fragment.news.adapter.NewsAdapter


class News : BaseFragment<FragmentNewsBinding, NewsViewModel>() {
    private var adapter: NewsAdapter? = null

    companion object {
        const val TAG = "tag"

        @JvmStatic
        fun newInstance(argModel: ArgObject) =
            News().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG, argModel)
                }
            }
    }

    override val layoutRes: Int = R.layout.fragment_news


    override fun initFragmentView() {
        stateVMListener()
        binding?.let { bind ->
            bind.event = viewModel
            adapter = NewsAdapter(viewModel)
            displayNewsInit()
            if (arguments != null) {
                val argObject = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelable(TAG, ArgObject::class.java)
                } else {
                    arguments?.getParcelable<ArgObject>(TAG)
                }
                argObject?.let { viewModel.init(it) }
            }
        }
    }

    override fun attachFragment() {}

    override fun startFragment() {
        disposable?.let {
            if (it.size() == 0) stateVMListener()
        }
    }

    override fun stopFragment() {
        disposable?.clear()
    }

    override fun destroyFragment() {
        adapter = null
        viewModel.onDestroyView()
    }

    override fun pauseFragment() {

    }

    override fun resume() {

    }

    fun stateVM() {

    }


    override fun stateVMListener() {
        disposable?.clear()
        byViewModel(viewModel as IState, object : ActionState<StateFlow> {
            override fun <T : StateFlow> action(model: T) {
                when (model.status) {
                    EnumStateFlow.STATUS_OK_NEWS_LIST.const -> {
                        adapter?.updateList(model.modelNews)
                    }

                    EnumStateFlow.STATUS_MGS.const -> {
                        showMessage(model.message)
                    }

                    EnumStateFlow.STATUS_LINK.const -> {
                        shareFile(model.message)
                    }
                }
            }
        }, object : ActionError {
            override fun error(msg: String) {
                showMessage("error ".plus(msg))
            }
        })
    }


    private fun displayNewsInit() =
        binding?.let {
            it.rvNewsDisplay.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.rvNewsDisplay.adapter = adapter
        }
}