package com.arch.presentation.fragment.favorites

import androidx.recyclerview.widget.LinearLayoutManager
import com.arch.portdomain.model.EnumStateFlow
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentNewsFavoritesBinding
import com.arch.presentation.fragment.favorites.adapter.FavoritesAdapter
import com.arch.presentation.fragment.news.News
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber


class NewsFavorites : BaseFragment<FragmentNewsFavoritesBinding, NewsFavoritesVM>(){
    private lateinit var adapter: FavoritesAdapter

    companion object {
        @JvmStatic
        fun newInstance() = NewsFavorites()
    }


    override val layoutRes: Int = R.layout.fragment_news_favorites

    override fun initFragmentView() {
        binding?.event = viewModel
        initAdapter()
        viewModel.init()
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
        if (disposable?.isDisposed != true) disposable?.clear()
        val subscribeState = viewModel.state()
        disposable?.add(subscribeState.subscribe({
            when (it.status) {
                EnumStateFlow.STATUS_OK_NEWS.const -> {
                    adapter.deleteItem(it.modelNews[0])
                    showMessage("delete ok")
                }
                EnumStateFlow.STATUS_OK_NEWS_LIST.const -> {
                    adapter.updateListAdapter(it.modelNews)
                }
                EnumStateFlow.STATUS_MGS.const -> {
                    showMessage(it.message)
                }
            }
        },{
            Timber.tag(News::class.java.name.toString())
                .i("error observationState : ".plus(it.message.toString()))
            showMessage("error ".plus(it.message))
        }))
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