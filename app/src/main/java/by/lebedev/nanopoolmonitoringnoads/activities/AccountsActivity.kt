package by.lebedev.nanopoolmonitoringnoads.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.activities.recycler.accounts.AccountAdapter
import by.lebedev.nanopoolmonitoringnoads.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.room.DataBase
import by.lebedev.nanopoolmonitoringnoads.room.entity.Account
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.account_layout.*
import javax.inject.Inject


class AccountsActivity : AppCompatActivity() {
    private var back_pressed: Long = 0


    @Inject
    lateinit var accountLocalList: AccountLocalList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_layout)

        val component = DaggerMagicBox.builder().build()
        accountLocalList = component.provideAccountLocalList()

        val fab = fabAddAccount
        fab.alpha = 0.6f
        fab.setOnClickListener {
            val intent = Intent(it.context, AddAccountActivity::class.java)
            startActivity(intent)
        }

        getAllDatabase()
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
                if (result.isEmpty()) {
                    noAccountText.visibility = View.VISIBLE
                } else {
                    noAccountText.visibility = View.INVISIBLE
                    setupRecycler(result)
                    accountLocalList.list = result as ArrayList<Account>
                }
                progressAccountLoad.visibility = View.GONE
            }
    }

    override fun onResume() {
        super.onResume()
        progressAccountLoad.visibility = View.VISIBLE
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