package by.lebedev.nanopoolmonitoring.activities.webview

import android.webkit.WebView
import android.webkit.WebViewClient

class SimpleWebView: WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }
}