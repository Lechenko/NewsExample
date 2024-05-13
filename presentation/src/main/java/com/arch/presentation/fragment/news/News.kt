package com.arch.presentation.fragment.news

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.ArgObject
import com.arch.portdomain.model.EnumStateFlow
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentNewsBinding
import com.arch.presentation.fragment.news.adapter.NewsAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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

    @SuppressLint("NewApi")
    override fun initFragmentView() {
        binding?.let { bind ->
            bind.event = viewModel
            adapter = NewsAdapter(viewModel)
            displayNewsInit()
            if (arguments != null) {
                val argObject = arguments?.getParcelable(TAG, ArgObject::class.java)
                argObject?.let { viewModel.init(it) }
            }
        }
    }

    override fun attachFragment() {}

    override fun startFragment() {
        if (disposable?.isDisposed != true) disposable?.clear()
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

     private fun displayNewsInit() {
         binding?.rvNewsDisplay?.layoutManager =
             LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
         binding?.rvNewsDisplay?.adapter = adapter
    }



}