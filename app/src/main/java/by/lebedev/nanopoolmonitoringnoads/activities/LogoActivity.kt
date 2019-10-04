package by.lebedev.nanopoolmonitoringnoads.activities

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoringnoads.R
import kotlinx.android.synthetic.main.logo_layout.*


class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_layout)

        val progress = logoProgress.getBackground() as AnimationDrawable
        progress.start()
        sleepAndTransit()
    }

    private fun sleepAndTransit() {

        android.os.Handler().postDelayed({ val intent = Intent(this, AccountsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.entering, R.anim.exiting) }, 200)

    }
}  