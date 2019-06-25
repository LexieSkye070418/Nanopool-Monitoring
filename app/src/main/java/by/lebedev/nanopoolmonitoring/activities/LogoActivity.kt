package by.lebedev.nanopoolmonitoring.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.lebedev.nanopoolmonitoring.R


class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_layout)
        sleepAndTransit()
    }

    private fun sleepAndTransit() {

        android.os.Handler().postDelayed({ val intent = Intent(this, AccountsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.entering, R.anim.exiting) }, 200)

    }
}  