package com.arch.presentation.fragment.group

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.EnumStateLayer
import com.arch.portdomain.model.EventState
import com.arch.portdomain.model.StateLayer
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.base.IState
import com.arch.presentation.databinding.FragmentNewsGroupBinding
import com.arch.presentation.fragment.group.adapter.lang.NewsLanguageAdapter
import com.arch.presentation.fragment.group.adapter.news.NewsGroupAdapter

class NewsGroup : BaseFragment<FragmentNewsGroupBinding,NewsGroupVM>() {
    private lateinit var adapterNewsGroup : NewsGroupAdapter
    private lateinit var adapterLanguage : NewsLanguageAdapter

    companion object {
        @JvmStatic
        fun newInstance() = NewsGroup()
    }



    override fun listenerViewModel(){
       disposable?.clear()
        byViewModel(viewModel as IState, object : ActionState<StateLayer> {
            override fun <T : StateLayer> action(model: T) {
                when (model.status) {
                    EnumStateLayer.STATUS_OK_GROUP_LIST.const -> {
                        adapterNewsGroup.updateAdapter(model.modelGroup)
                    }

                    EnumStateLayer.STATUS_MGS.const -> {
                        showMessage(model.message)
                    }

                    EnumStateLayer.STATUS_EVENT.const -> {
                        model.message.let {msg -> if (EventState.UPDATE_ADAPTER.const == msg)
                            adapterLanguage.updateAdapter() }
                    }
                }
            }
        }, object : ActionError {
            override fun error(msg: String) {
                showMessage("error ".plus(msg))
            }
        })
    }


    override val layoutRes: Int = R.layout.fragment_news_group

    override fun initFragmentView() {
        listenerViewModel()
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
         if (it.size() == 0) listenerViewModel()
     }
    }

    override fun stopFragment() {
        disposable?.clear()
    }

    override fun destroyFragment() {
        viewModel.onDestroyView()
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