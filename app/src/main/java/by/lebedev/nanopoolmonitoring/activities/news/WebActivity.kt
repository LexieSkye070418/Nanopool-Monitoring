package by.lebedev.nanopoolmonitoring.activities.news

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R
import kotlinx.android.synthetic.main.item_coin.*
import kotlinx.android.synthetic.main.item_news.*
import kotlinx.android.synthetic.main.web_fragment.*
import kotlinx.android.synthetic.main.web_fragment.view.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_fragment)

        webView.loadUrl(intent.getStringExtra("url"))

    }
}