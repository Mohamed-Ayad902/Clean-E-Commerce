package com.mayad7474.cleane_commerce.core.extensions

import android.util.Log
import com.mayad7474.cleane_commerce.BuildConfig

fun Any.logd(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.d(tag, "$tag: $this")
}

fun Any.loge(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.e(tag, "$tag: $this")
}

fun Any.logi(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.i(tag, "$tag: $this")
}

fun Any.logv(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.v(tag, "$tag: $this")
}

fun Any.logw(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.w(tag, "$tag: $this")
}