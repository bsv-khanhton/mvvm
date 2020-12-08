package vn.com.bravesoft.androidapp.ext

import android.util.Log
import vn.com.bravesoft.androidapp.BuildConfig

fun Any.logi() {
    if (BuildConfig.DEBUG) {
        Log.i("BSV", "--------------------------------------")
        Log.i("BSV", "--- $this")
    }
}

fun Any.logd() {
    if (BuildConfig.DEBUG) {
        Log.i("BSV", "--------------------------------------")
        Log.d("BSV", "--- $this")
    }
}

fun Any.loge() {
    if (BuildConfig.DEBUG) {
        Log.i("BSV", "--------------------------------------")
        Log.e("BSV", "--- $this")
    }
}
