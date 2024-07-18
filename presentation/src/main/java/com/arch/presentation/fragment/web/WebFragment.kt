package com.arch.presentation.fragment.web

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.arch.portdomain.model.NewsModel
import com.arch.presentation.R
import com.arch.presentation.base.BaseFragment
import com.arch.presentation.databinding.FragmentWebBinding
import timber.log.Timber


@Suppress("DEPRECATION")
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
    @Suppress("DEPRECATION")
    inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? {
        Timber.d("intentextras parce")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getParcelableExtra(key, T::class.java)
        } else {
            this.getParcelableExtra(key)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Suppress("DEPRECATION")
    override fun initFragmentView() {
        binding?.let {bind ->
            bind.event = viewModel
            if (arguments != null) {
                val newsModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelable(TAG, NewsModel::class.java)
                    } else {
                        arguments?.getParcelable(TAG)
                    }
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

    @SuppressLint("SetJavaScriptEnabled", "NewApi")
    private fun setupBrowser(url: String) {
//        binding?.let { bind ->{
//            val webSettings: WebSettings = bind.wvsWeb.settings
//            webSettings.allowContentAccess = true
//            webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
//            bind.wvsWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//            bind.wvsWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//            bind.wvsWeb.webViewClient = WebViewClient()
//            webChrome()
//            bind.wvsWeb.loadUrl(url)
//        } }
        binding?.let {
            val webSettings = it.wvsWeb.settings
            webSettings.javaScriptEnabled = true
            webSettings.javaScriptCanOpenWindowsAutomatically
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            it.wvsWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
            it.wvsWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            it.wvsWeb.webViewClient = WebViewClient()
            webChrome()
            Timber.tag(WebFragment::class.simpleName.toString())
                .e("URL NEWS : ".plus(url))
            it.wvsWeb.loadUrl(url)
        }
       //

    }

    private fun webChrome() {
        binding?.let {bind ->
            bind.wvsWeb.webChromeClient = object : WebChromeClient() {
                @SuppressLint("UseCompatLoadingForDrawables")
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    if (context != null) {
                        bind.pbWebProgressBar.progress = newProgress
                        bind.pbWebProgressBar.progressDrawable =
                            context?.resources?.getDrawable(R.drawable.progress_indicator)
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
        viewModel.onDestroyView()
    }

    override fun pauseFragment() {

    }

    override fun resume() {

    }

    override fun listenerViewModel(){

    }
}