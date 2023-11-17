package vn.com.bravesoft.androidapp.ui

import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import vn.com.bravesoft.androidapp.R
import vn.com.bravesoft.androidapp.base.BaseFragment
import vn.com.bravesoft.androidapp.databinding.WebviewLayoutBinding
import vn.com.bravesoft.androidapp.ext.argumentNullable

class WebViewFragment : BaseFragment(R.layout.webview_layout) {
    private val binding: WebviewLayoutBinding by viewBinding()

    private var videoID: String? by argumentNullable()

    private val html =
        """
        <iframe id="player" src="https://www.youtube.com/embed/HsLvnFQW_yM" allowfullscreen></iframe>
        """.trimIndent()

    companion object {
        fun newInstance(videoID: String?): WebViewFragment = WebViewFragment().apply {
            this.videoID = videoID
        }
    }

    override fun init(view: View) {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        val webClient = object : WebViewClient() {}
        binding.webView.webViewClient = webClient
        binding.webView.webChromeClient = WebChromeClient()
        if (videoID != null) {
            val htmlCode = html.replace("XXX", videoID!!)

            binding.webView.loadData(htmlCode, "text/html", "UTF-8")
        } else {
            binding.webView.loadUrl("https://bravesoft.co.jp/")
        }
    }
}
