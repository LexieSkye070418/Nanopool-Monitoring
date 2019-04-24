package by.lebedev.nanopoolmonitoring.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.activities.recycler.accounts.AccountAdapter
import by.lebedev.nanopoolmonitoring.coins.AccountLocalList
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.accounts_layout.*

class AccountsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var skip: Button
    private var back_pressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(by.lebedev.nanopoolmonitoring.R.layout.accounts_layout)
        skip = accountsButtonSkip
        skip.setOnClickListener(this)

        val fab = fabAddAccount
        fab.setOnClickListener {
            val intent = Intent(it.context, AddAccountActivity::class.java)
            startActivity(intent)
        }

        getAllDatabase()

    }

    override fun onClick(v: View?) {
        val intent = Intent(this, TabActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            by.lebedev.nanopoolmonitoring.R.anim.entering,
            by.lebedev.nanopoolmonitoring.R.anim.exiting
        )
    }

    fun setupRecycler(accountList: List<Account>) {
        recycleAccounts.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycleAccounts.layoutManager = layoutManager
        recycleAccounts.adapter = AccountAdapter(accountList, this)
    }

    fun getAllDatabase() {
        val d = DataBase.getInstance(this).db.accountDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                setupRecycler(result)
                AccountLocalList.instance.list = result as ArrayList<Account>
                if (result.isEmpty()) {
                    skip.visibility = View.VISIBLE
                }
                progressAccountLoad.visibility = View.GONE
            }
    }

    override fun onResume() {
        skip.visibility = View.INVISIBLE
        super.onResume()
        getAllDatabase()
    }

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            moveTaskToBack(true);
            finish();
            System.exit(0);

        } else {
            Toast.makeText(baseContext, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
        }

        back_pressed = System.currentTimeMillis()
    }
}