package by.lebedev.nanopoolmonitoring.activities.webview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import by.lebedev.nanopoolmonitoring.R
import kotlinx.android.synthetic.main.webview_layout.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_layout)

        val simpleWebView = SimpleWebView()
        webview.webViewClient = simpleWebView

        webview.settings.javaScriptEnabled = true
        var url: String = intent.getStringExtra("url")
        webview.loadUrl(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
