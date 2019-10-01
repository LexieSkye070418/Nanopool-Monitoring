package by.lebedev.nanopoolmonitoring.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoring.dagger.PoolCoins
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adding_account_layout.addAccCoinLogo
import kotlinx.android.synthetic.main.adding_account_layout.coinName
import kotlinx.android.synthetic.main.adding_account_layout.selectCoinButton
import kotlinx.android.synthetic.main.modify_account_layout.*
import java.util.*
import javax.inject.Inject


class ModifyAccountActivity : AppCompatActivity() {

    private var coinId: Int = 0
    private var accountId: Long = -1
    private var position: Int = -1
    @Inject
    lateinit var poolCoins: PoolCoins
    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData
    @Inject
    lateinit var accountLocalList: AccountLocalList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_account_layout)
        window.setBackgroundDrawableResource(R.drawable.nanopool_background)

        val component = DaggerMagicBox.builder().build()
        poolCoins = component.providePoolCoins()
        coinWalletTempData = component.provideTabIntent()
        accountLocalList = component.provideAccountLocalList()
        val arrayOfCoins = arrayOfNulls<String>(poolCoins.list.size)
        poolCoins.list.toArray(arrayOfCoins)

        coinWalletTempData.coin = intent.getStringExtra("COIN")
        coinWalletTempData.wallet = intent.getStringExtra("WALLET")
        accountId = intent.getLongExtra("ID", -1)
        position = intent.getIntExtra("POSITION", -1)

        setSelectedCoinImage(poolCoins.list.indexOf(coinWalletTempData.coin))

        val walletText = modifyAccountEditText
        val modifyButton = modifyButton
        val cancelButton = cancelButton

        coinId = poolCoins.list.indexOf(coinWalletTempData.coin)
        walletText.setText(coinWalletTempData.wallet)

        coinName.setText("Selected coin: " + poolCoins.fullName(coinId))

        cancelButton.setOnClickListener {
            onBackPressed()
        }

        modifyButton.setOnClickListener {

            if (walletText.text.toString() != "") {
                updateDatabase(accountId, poolCoins.list.get(coinId), walletText.text.toString())

                Log.e(
                    "FF",
                    accountId.toString() + "    " + poolCoins.list.get(coinId) + "   " + walletText.text.toString()
                )

            } else {
                walletText.setError("please, fill this field")
                Toast.makeText(baseContext, "Please, enter your wallet", Toast.LENGTH_SHORT).show()
            }
        }

        selectCoinButton.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
                .setTitle("Select your coin")
                .setIcon(R.drawable.bitcoinicon)
                .setCancelable(true)

                .setSingleChoiceItems(
                    arrayOfCoins, -1
                ) { _, item ->

                    coinId = item
                    setSelectedCoinImage(coinId)
                    coinName.setText("Selected coin: " + poolCoins.fullName(coinId))

                }


                .setPositiveButton("OK") { dialog, which -> dialog.cancel() }
                .setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        }
    }


    fun updateDatabase(accountId: Long, newCoin: String, newWallet: String) {
        val complete = Completable.fromAction {
            DataBase.getInstance(this).db.accountDao().modify(
                Account(accountId, newCoin, newWallet)
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                accountLocalList.list.get(position).coin = newCoin
                accountLocalList.list.get(position).wallet = newWallet
                onBackPressed()
            }) {
                Log.e("err", it.message)

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