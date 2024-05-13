package com.arch.presentation.fragment.web

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentWebBinding


class WebFragment : BaseFragment<FragmentWebBinding,WebVM>() {

    companion object {
        private const val TAG = "model"
        @JvmStatic
        fun newInstance(newsModel: NewsModel) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG,newsModel)
                }
            }
    }

    override val layoutRes: Int = R.layout.fragment_web

    @SuppressLint("NewApi")
    override fun initFragmentView() {
        binding?.let {bind ->
            bind.event = viewModel
            if (arguments != null) {

                val newsModel = arguments?.getParcelable(TAG, NewsModel::class.java)
                if (newsModel != null) {
                    bind.item = newsModel
                    if (newsModel.id != 0L) bind.ivWebFavorites.visibility = View.GONE
                    newsModel.url?.let { setupBrowser(it) }
                    bind.ivWebShare.setOnClickListener {
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_SEND
                        sendIntent.putExtra(Intent.EXTRA_TEXT, bind.wvsWeb.url)
                        sendIntent.type = "text/plain"
                        startActivity(sendIntent)
                    }
                } else {

                }
            }
        }
    }

    private fun setupBrowser(url: String) {
        binding?.let { bind ->{
            val webSettings: WebSettings = bind.wvsWeb.settings
            webSettings.javaScriptCanOpenWindowsAutomatically
            bind.wvsWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
            bind.wvsWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            bind.wvsWeb.webViewClient = WebViewClient()
            webChrome()
            bind.wvsWeb.loadUrl(url)
        } }
    }

    private fun webChrome() {
        binding?.let {bind ->
            bind.wvsWeb.webChromeClient = object : WebChromeClient() {
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    if (context != null) {
                        bind.pbWebProgressBar.progress = newProgress
                        bind.pbWebProgressBar.progressDrawable =
                            context?.resources?.getDrawable(R.drawable.progress_indicator,null)
                        bind.pbWebProgressBar.visibility = if (newProgress == 100) View.GONE else View.VISIBLE
                    }
                }
            }
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