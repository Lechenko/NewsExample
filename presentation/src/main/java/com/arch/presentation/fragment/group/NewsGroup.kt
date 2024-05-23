package com.arch.presentation.fragment.group

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.EventState
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentNewsGroupBinding
import com.arch.presentation.fragment.group.adapter.lang.NewsLanguageAdapter
import com.arch.presentation.fragment.group.adapter.news.NewsGroupAdapter
import com.arch.presentation.fragment.news.News
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class NewsGroup : BaseFragment<FragmentNewsGroupBinding,NewsGroupVM>() {
    private lateinit var adapterNewsGroup : NewsGroupAdapter
    private lateinit var adapterLanguage : NewsLanguageAdapter

    companion object {
        @JvmStatic
        fun newInstance() = NewsGroup()
    }



    private fun stateVMListener(){
        if (disposable?.isDisposed != true) disposable?.clear()
       // val subscribeState = viewModel.state()
        disposable?.add(viewModel.state().subscribe({
            when (it.status) {
                EnumStateFlow.STATUS_OK_GROUP_LIST.const -> {
                    adapterNewsGroup.updateAdapter(it.modelGroup)
                }
                EnumStateFlow.STATUS_MGS.const -> {
                    showMessage(it.message)
                }
                EnumStateFlow.STATUS_EVENT.const -> {
                    it.message.let {msg -> if (EventState.UPDATE_ADAPTER.const == msg)
                        adapterLanguage.updateAdapter() }
                }
            }
        },{
            Timber.tag(News::class.java.name.toString())
                .i("error observationState : ".plus(it.message.toString()))
            showMessage("error ".plus(it.message))
        }))
    }


    override val layoutRes: Int = R.layout.fragment_news_group

    override fun initFragmentView() {
        stateVMListener()
        binding?.let {
            it.event = viewModel
            displayLanguage()
            displayGroup()
            viewModel.init()
            it.swipeRefreshRv.setOnRefreshListener {
                viewModel.init()
                it.swipeRefreshRv.isRefreshing = false
            }
        }
    }

    override fun attachFragment() {

    }

    override fun startFragment() {
     disposable?.let {
         if (it.size() == 0) stateVMListener()
     }
    }

    override fun stopFragment() {
        disposable?.clear()
    }

    override fun destroyFragment() {

    }

    override fun pauseFragment() {

    }

    override fun resume() {

    }

    private fun displayGroup() =
        binding?.let {
            it.rvNewsCategoriesDisplay.layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.category_grid_count))
            adapterNewsGroup = NewsGroupAdapter(viewModel)
            it.rvNewsCategoriesDisplay.adapter = adapterNewsGroup
        }




   private  fun displayLanguage() =
         binding?.let {
             it.rvNewsCategoryLanguage.layoutManager =
                 LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
             adapterLanguage = NewsLanguageAdapter(viewModel)
             it.rvNewsCategoryLanguage.adapter = adapterLanguage
         }

}