package vn.com.bravesoft.androidapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.webkit.WebView

class CustomWebview : WebView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return false
    }
}