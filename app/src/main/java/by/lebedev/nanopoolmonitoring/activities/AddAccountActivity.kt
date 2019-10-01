package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.PoolCoins
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
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
        window.setBackgroundDrawableResource(R.drawable.nanopool_background)
        addAccCoinLogo.setImageResource(R.drawable.eth)

        val component = DaggerMagicBox.builder().build()
        poolCoins = component.providePoolCoins()
        val arrayOfCoins = arrayOfNulls<String>(poolCoins.list.size)
        poolCoins.list.toArray(arrayOfCoins)

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

        selectCoinButton.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
                .setTitle("Select your coin")
                .setIcon(R.drawable.bitcoinicon)
                .setCancelable(true)

                .setSingleChoiceItems(arrayOfCoins, -1,
                    { dialog, item ->

                        coinId = item
                        setSelectedCoinImage(coinId)
                        coinName.setText("Selected coin: " + poolCoins.fullName(coinId))

                    })


                .setPositiveButton("OK", { dialog, which -> dialog.cancel() })
                .setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
            val alert = builder.create()
            alert.show()
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
                addAccCoinLogo.setImageResource(R.drawable.raven)

            }
            6 -> {
                addAccCoinLogo.setImageResource(R.drawable.grin)

            }
            else -> addAccCoinLogo.setImageResource(R.drawable.eth)
        }
    }
}