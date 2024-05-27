package com.arch.presentation.fragment.favorites

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.portdomain.model.StateFlow
import com.arch.presentation.R
import com.arch.presentation.activity.MainActivity
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.base.IState
import com.arch.presentation.databinding.FragmentNewsFavoritesBinding
import com.arch.presentation.fragment.favorites.adapter.FavoritesAdapter
import com.arch.presentation.fragment.news.News
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import timber.log.Timber


class NewsFavorites : BaseFragment<FragmentNewsFavoritesBinding, NewsFavoritesVM>(){
    private lateinit var adapter: FavoritesAdapter

    companion object {
        @JvmStatic
        fun newInstance() = NewsFavorites()
    }

    override fun stateVMListener() {
        disposable?.clear()
        subscribeStateVM(viewModel as IState, object : ActionState<StateFlow> {
            override fun <T : StateFlow> action(model: T) {
                when (model.status) {
                    EnumStateFlow.STATUS_OK_NEWS.const -> {
                        adapter.deleteItem(model.modelNews[0])
                        showMessage("delete ok")
                    }

                    EnumStateFlow.STATUS_OK_NEWS_LIST.const -> {
                        adapter.updateListAdapter(model.modelNews)
                    }

                    EnumStateFlow.STATUS_MGS.const -> {
                        showMessage(model.message)
                    }
                }
            }
        }, object : ActionError {
            override fun error(msg: String) {
                showMessage("error ".plus(msg))
            }
        })
    }


    override val layoutRes: Int = R.layout.fragment_news_favorites

    override fun initFragmentView() {
        binding?.event = viewModel
        stateVMListener()
        initAdapter()
        viewModel.init()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repeatOnLifecycle(Lifecycle.State.DESTROYED) {
                    viewModel.onDestroyView()
                    disposable?.clear()
                }
            }
        }
    }


    private fun initAdapter() {
        binding?.rvFavoritesDisplay?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = FavoritesAdapter(viewModel)
        binding?.rvFavoritesDisplay?.adapter = adapter
    }

    override fun attachFragment() {

    }

    override fun startFragment() {
    }

    override fun stopFragment() {

    }

    override fun destroyFragment() {
        viewModel.onDestroyView()
    }

    override fun pauseFragment() {

    }

    override fun resume() {

    }
}