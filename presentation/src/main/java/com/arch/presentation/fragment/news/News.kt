package com.arch.presentation.fragment.news

import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentNewsBinding
import com.arch.presentation.fragment.news.adapter.NewsAdapter
import timber.log.Timber


class News : BaseFragment<FragmentNewsBinding,NewsViewModel>(){
    private var adapter : NewsAdapter ? = null
    companion object {
        const val TAG = "tag"
        @JvmStatic
        fun newInstance(argModel : ArgObject) =
            News().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG,argModel)
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
        disposable?.dispose()
        disposable = null
        adapter = null
        viewModel.onDestroyView()
    }

    override fun pauseFragment() {

    }

    override fun resume() {

    }

    private fun stateVMListener(){
        disposable?.clear()
        val subscribeState = viewModel.state()
        disposable?.add(subscribeState.subscribe({
            when (it.status) {
                EnumStateFlow.STATUS_OK_NEWS_LIST.const -> {
                    adapter?.updateList(it.modelNews)
                }
                EnumStateFlow.STATUS_MGS.const -> {
                    showMessage(it.message)
                }
                EnumStateFlow.STATUS_LINK.const ->{
                    shareFile(it.message)
                }
            }
        },{
            Timber.tag(News::class.java.name.toString())
                .i("error observationState : ".plus(it.message.toString()))
            showMessage("error ".plus(it.message))
        }))
    }

     private fun displayNewsInit() =
         binding?.let {
             it.rvNewsDisplay.layoutManager =
                 LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
             it.rvNewsDisplay.adapter = adapter
         }
}