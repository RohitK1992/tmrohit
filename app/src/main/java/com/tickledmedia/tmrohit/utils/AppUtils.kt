package com.tickledmedia.tmrohit.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import com.tickledmedia.tmrohit.constants.Constants
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tickledmedia.tmrohit.BuildConfig
import com.tickledmedia.tmrohit.R
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object AppUtils {

    @JvmStatic
    fun hideSoftKeyboard(activity: Activity?) {
        if (activity != null) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @JvmStatic
    fun setStatusBarType(mActivity: Activity, type: Constants.StatusBar?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (type) {
                Constants.StatusBar.LIGHT -> {
                    mActivity.window.statusBarColor =
                            ContextCompat.getColor(mActivity, R.color.white)
                    mActivity.window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                Constants.StatusBar.DARK -> {
                    mActivity.window.statusBarColor = ContextCompat.getColor(
                            mActivity,
                            R.color.black
                    )
                    mActivity.window.decorView.systemUiVisibility = 0
                }
                Constants.StatusBar.FULLSCREEN -> mActivity.window
                        .setFlags(
                                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        )
            }
        }
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    @JvmStatic
    fun hourDifference(dbTime: Long, currentTime: Long): Long {
        return TimeUnit.MILLISECONDS.toHours(currentTime - dbTime)
    }

    /* Extension Function*/
    fun RecyclerView.removeItemDecorations() {
        while (this.itemDecorationCount > 0) {
            this.removeItemDecorationAt(0)
        }
    }

    @JvmStatic
    fun showLongToastMessage(context: Context, strMessage: String) {
        Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show()
    }

    @JvmStatic
    fun showShortToastMessage(context: Context, strMessage: String) {
        Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun eLog(strKey: String, strMessage: String) {
        if(BuildConfig.DEBUG){
            Log.e(strKey,strMessage?:"");
        }
    }
}