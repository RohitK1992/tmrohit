package com.tickledmedia.tmrohit.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import com.tickledmedia.tmrohit.BuildConfig

object AppVersion {
    @JvmStatic
    fun getAppVersionCode(context: Context): Long {
        var version: Long
        version = BuildConfig.VERSION_CODE.toLong()
        if (0L == version) {
            version = try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    packageInfo.versionCode.toLong()
                }
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException("Could not get package name: $e")
            }

        }
        return version
    }

    @JvmStatic
    fun getAppVersionName(context: Context): String? {
        var version: String? = null
        version = BuildConfig.VERSION_NAME
        if (TextUtils.isEmpty(version)) {
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                version = packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException("Could not get package name: $e")
            }

        }
        return version
    }


    fun hasMinimumSdk(minimumSdk: Int) = Build.VERSION.SDK_INT >= minimumSdk

    fun hasMaximumSdk(maximumSdk: Int) = Build.VERSION.SDK_INT <= maximumSdk
}