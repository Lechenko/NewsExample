package com.arch.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.arch.presentation.R
import com.arch.presentation.base.BaseActivity
import com.arch.presentation.databinding.ActivityMainBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import timber.log.Timber

import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(),
    IMainView.View {
    private var disposable : CompositeDisposable? = CompositeDisposable()

//    @Inject
//    lateinit var viewModel: MainViewModel

//    private val viewModel by lazy{
//        ViewModelProvider(this, factory)[MainViewModel::class.java]
//    }

    override val layoutRes: Int = R.layout.activity_main

    override fun initOnCreate() {

    }

    override fun createActivity(savedInstanceState: Bundle?) {
        binding?.let {
            it.router = viewModel.funBindingRouter()
            viewModel.initDrawerLayout(it.drawerLayout)
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startFragmentMain()
                disposable?.add(viewModel.state().subscribe({

                }, {
                    Timber.tag(MainActivity::class.java.name)
                        .e("error viewModel.state() ".plus(it.message))
                }))
            }
            repeatOnLifecycle(Lifecycle.State.DESTROYED) {
               viewModel.onDestroyView()
               disposable?.clear()
            }
        }
    }

    override fun stopActivity() {

    }

    override fun startActivity() {

    }

    override fun pauseActivity() {

    }

    override fun resumeActivity() {

    }

    override fun destroyActivity() {

    }


    override fun setAppBarText(name: String) {

    }

    override fun hideAppBar(visible: Boolean) {

    }

    override fun onMessage(message: String) {
        super.toastLong(message)
    }

    override fun isProgress(flag: Boolean) {
        binding?.let {
            it.progressBar.visibility = if (flag) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun hideBottomNavigation(flag: Boolean) {

    }
}