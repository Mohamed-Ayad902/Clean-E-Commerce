package com.mayad7474.cleane_commerce.core.extensions

import android.util.Log
import com.mayad7474.cleane_commerce.BuildConfig

fun <T : Any> Any.logd(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.d(tag, "$tag: $this")
}

fun <T : Any> Any.loge(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.e(tag, "$tag: $this")
}

fun <T : Any> Any.logi(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.i(tag, "$tag: $this")
}

fun <T : Any> Any.logv(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.v(tag, "$tag: $this")
}

fun <T : Any> Any.logw(tag: String = "AppDebug") {
    if (BuildConfig.DEBUG)
        Log.w(tag, "$tag: $this")
}