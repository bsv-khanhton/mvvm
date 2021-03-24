package vn.com.bravesoft.androidapp.ui

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.webkit.WebView
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment

/**
 * Created by Khanh Ton on 12/8/20.
 */
class OpenSourceLicensesDialog : DialogFragment() {
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val webView = WebView(requireActivity())
        webView.loadUrl("file:///android_asset/open_source_licenses.html")
        return AlertDialog.Builder(requireActivity())
            .setTitle("Open Source Licenses")
            .setView(webView)
            .setPositiveButton(R.string.ok, { dialog, which -> dialog.dismiss() })
            .create()
    }
}
