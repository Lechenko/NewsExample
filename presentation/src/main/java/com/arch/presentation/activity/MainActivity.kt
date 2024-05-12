package com.arch.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arch.presentation.R
import com.arch.presentation.base.BaseActivity
import com.arch.presentation.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(),
    IMainView.View {
    @Inject
    lateinit var viewModel: MainViewModel
//    private val viewModel by lazy{
//        ViewModelProvider(this, factory)[MainViewModel::class.java]
//    }

    override val layoutRes: Int = R.layout.activity_main

    override fun initOnCreate() {

    }

    override fun createActivity(savedInstanceState: Bundle?) {
        binding.router = viewModel.funBindingRouter()
        viewModel.initDrawerLayout(binding.drawerLayout)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startFragmentMain()
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
        binding.progressBar.visibility = if(flag) View.VISIBLE else View.INVISIBLE
    }

    override fun hideBottomNavigation(flag: Boolean) {

    }
}