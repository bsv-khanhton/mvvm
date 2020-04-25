package vn.com.bravesoft.androidapp.utils

import android.util.Log

import vn.com.bravesoft.androidapp.BuildConfig

object LogUtil {
    //TAG Class
    internal var TAG = "BSV"


    // --------------------------------------------------------
    // Show Log info
    @Synchronized
    fun log(content: String) {

        if (BuildConfig.DEBUG) {
            Log.i("", "--------------------------------------")
            Log.i(TAG, "--- $content")
        }
    }

}
