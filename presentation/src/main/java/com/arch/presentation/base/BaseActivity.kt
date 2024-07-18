package com.arch.presentation.base
import android.util.Pair
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.arch.presentation.activity.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import java.io.File
import java.util.Objects
import javax.inject.Inject


abstract class BaseActivity<Binding : ViewDataBinding,VIEW_MODEL : ViewModel> : DaggerAppCompatActivity() {
    var binding: Binding? = null
        private set
    @Inject
    lateinit var viewModel: VIEW_MODEL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOnCreate()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        createActivity(savedInstanceState)
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract fun initOnCreate()
    protected abstract fun createActivity(savedInstanceState: Bundle?)
    protected abstract fun stopActivity()
    protected abstract fun startActivity()
    protected abstract fun pauseActivity()
    protected abstract fun resumeActivity()
    protected abstract fun destroyActivity()
   // protected abstract fun getPresenter(): BasePresenter

    override fun onDestroy() {
     //   getPresenter().destroyView()
        destroyActivity()
        binding?.unbind()
        binding = null
        super.onDestroy()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val manager = supportFragmentManager
        if (manager.backStackEntryCount == 1) {
            finish()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    protected fun toastShort(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun toastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    override fun onStart() {
        super.onStart()
    //    getPresenter().startView()
        startActivity()
    }


    override fun onStop() {
      //  getPresenter().stopView()
        stopActivity()
        super.onStop()
    }

    override fun onPause() {
        pauseActivity()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        resumeActivity()
    }

    @SuppressLint("NewApi")
    fun hideKeyboard() {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        Objects.requireNonNull(imm).hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
    }

    @SuppressLint("NewApi")
    fun showKeyboard() {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        Objects.requireNonNull(imm)
            .showSoftInput(this.window.decorView, InputMethodManager.SHOW_IMPLICIT)
    }

    protected fun share(path: String) {
        val f = File(path)
        val intentShareFile = Intent(Intent.ACTION_SEND)
        if (f.exists()) {
            val imageUri = FileProvider.getUriForFile(
                this,
                this.applicationContext.packageName + ".provider",
                f
            )
            intentShareFile.type = "file/*"
            intentShareFile.putExtra(Intent.EXTRA_STREAM, imageUri)
            intentShareFile.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val chooser = Intent.createChooser(intentShareFile, "Share File")
            @SuppressLint("QueryPermissionsNeeded") val resInfoList =
                this.packageManager.queryIntentActivities(
                    chooser,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                grantUriPermission(
                    packageName, imageUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            startActivity(chooser)
        }
    }
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    protected fun openIntent(path: Pair<String,String>){
        val file = File(path.second)
        val intent = Intent(Intent.ACTION_VIEW)
            .setDataAndType(
                FileProvider.getUriForFile(this, "$packageName.provider", file),
                if ("mp4" == path.first || "m4v" == path.first) "video/*" else "image/*"
            ).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intent, "Share Via"))
    }
}