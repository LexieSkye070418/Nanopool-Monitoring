package by.lebedev.nanopoolmonitoring.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import by.lebedev.nanopoolmonitoring.R


class LogoActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_layout)
        sleepAndTransit()
    }

    fun sleepAndTransit() {

        android.os.Handler().postDelayed({ val intent = Intent(this, AccountsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.entering, R.anim.exiting) }, 1000)

    }
}