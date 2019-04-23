package by.lebedev.nanopoolmonitoring.activities

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.coins.PoolCoins
import by.lebedev.nanopoolmonitoring.room.Account
import by.lebedev.nanopoolmonitoring.room.AppDatabase
import by.lebedev.nanopoolmonitoring.room.DataBase
import kotlinx.android.synthetic.main.add_account_layout.*
import java.util.*
import io.reactivex.Completable


class AddAccountActivity : AppCompatActivity() {

    var coinId: Int = 0
//    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(by.lebedev.nanopoolmonitoring.R.layout.add_account_layout)
        val wallet = addAccountEditText

        val button = addAccountButton
        button.setOnClickListener {

            if (wallet.text.toString()!=""){
            insertToDatabase(wallet.text.toString())
            onBackPressed()}
            else{
                Toast.makeText(baseContext, "Please, enter your wallet", Toast.LENGTH_SHORT).show()
            }
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PoolCoins.instance.list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = coinSpinner
        spinner.adapter = adapter

        setupSpinner(spinner)

//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "database"
//        ).build()


    }

    fun setupSpinner(spinner: Spinner) {

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                coinId = position
                Toast.makeText(baseContext, "Position = $position", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun insertToDatabase(walletText:String) {
        val d = Completable.fromAction {
            DataBase.instances.db.accountDao().insert(
                Account(
                    Calendar.getInstance().timeInMillis,
                    PoolCoins.instance.list.get(coinId),
                    walletText
                )
            )
        }
    }

}