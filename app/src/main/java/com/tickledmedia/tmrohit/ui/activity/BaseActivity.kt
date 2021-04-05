package com.tickledmedia.tmrohit.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tickledmedia.tmrohit.constants.Constants
import com.tickledmedia.tmrohit.utils.AppUtils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        init(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        AppUtils.setStatusBarType(this, getStatusBarType())
    }

    override fun onResume() {
        super.onResume()
        AppUtils.setStatusBarType(this, getStatusBarType())
    }

    protected abstract fun getStatusBarType(): Constants.StatusBar?

    protected abstract fun getScreenName(): String?

    protected abstract fun init(savedInstanceState: Bundle?)

    protected abstract fun getLayout(): Int

    protected abstract fun doNetworkWork()
}