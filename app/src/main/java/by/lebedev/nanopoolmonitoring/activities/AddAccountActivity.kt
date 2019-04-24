package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.dagger.PoolCoins
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_account_layout.*
import java.util.*


class AddAccountActivity : AppCompatActivity() {

    private var coinId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(by.lebedev.nanopoolmonitoring.R.layout.add_account_layout)
        val wallet = addAccountEditText

        val button = addAccountButton
        button.setOnClickListener {

            if (wallet.text.toString() != "") {
                insertToDatabase(wallet.text.toString())
                onBackPressed()
            } else {
                Toast.makeText(baseContext, "Please, enter your wallet", Toast.LENGTH_SHORT).show()
            }
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PoolCoins.instance.list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = coinSpinner
        spinner.adapter = adapter

        setupSpinner(spinner)

    }

    private fun setupSpinner(spinner: Spinner) {

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                coinId = position
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    private fun insertToDatabase(walletText: String) {
        val complete = Completable.fromAction {
            DataBase.getInstance(this).db.accountDao().insert(
                Account(
                    Calendar.getInstance().timeInMillis,
                    PoolCoins.instance.list.get(coinId),
                    walletText
                )
            )

            Log.e("AAA", "INSERTED")
        }
        val disposable = complete.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
    }
}