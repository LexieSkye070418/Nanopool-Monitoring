package by.lebedev.nanopoolmonitoringnoads.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.PoolCoins
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.room.DataBase
import by.lebedev.nanopoolmonitoringnoads.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adding_account_layout.*
import java.util.*
import javax.inject.Inject


class AddAccountActivity : AppCompatActivity() {

    private var coinId: Int = 0
    @Inject
    lateinit var poolCoins: PoolCoins

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adding_account_layout)

        val component = DaggerMagicBox.builder().build()
        poolCoins = component.providePoolCoins()

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

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, poolCoins.list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_nano)

        val spinner = coinSpinner
        spinner.prompt = "Coin"
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
                setSelectedCoinImage(coinId)
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun insertToDatabase(walletText: String) {
        val complete = Completable.fromAction {
            DataBase.getInstance(this).db.accountDao().insert(
                Account(
                    Calendar.getInstance().timeInMillis,
                    poolCoins.list.get(coinId),
                    walletText

                )
            )
        }
        val disposable = complete.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
    }

    fun setSelectedCoinImage(position: Int) {
        when (position) {
            0 -> {
                addAccCoinLogo.setImageResource(R.drawable.eth)

            }
            1 -> {
                addAccCoinLogo.setImageResource(R.drawable.etc)

            }
            2 -> {
                addAccCoinLogo.setImageResource(R.drawable.zec)

            }
            3 -> {
                addAccCoinLogo.setImageResource(R.drawable.xmr)

            }
            4 -> {
                addAccCoinLogo.setImageResource(R.drawable.pasc)

            }
            5 -> {
                addAccCoinLogo.setImageResource(R.drawable.etn)

            }
            6 -> {
                addAccCoinLogo.setImageResource(R.drawable.raven)

            }
            7 -> {
                addAccCoinLogo.setImageResource(R.drawable.grin)

            }
            else -> addAccCoinLogo.setImageResource(R.drawable.eth)
        }
    }

}