package by.lebedev.nanopoolmonitoring.activities.webview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import by.lebedev.nanopoolmonitoring.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.webview_layout.*

class WebActivity : AppCompatActivity() {

    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_layout)

        MobileAds.initialize(this, "ca-app-pub-1501215034144631~3780667725")

        val adView = AdView(this)
        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-1501215034144631/9714557621"


        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
