package by.lebedev.nanopoolmonitoring.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import by.lebedev.nanopoolmonitoring.R
import kotlinx.android.synthetic.main.accounts_layout.*

class AccountsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accounts_layout)
        val skip = accountsButtonSkip
        skip.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val intent = Intent(this,TabActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.entering, R.anim.exiting)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onDestroy()
    }
}