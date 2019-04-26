package by.lebedev.nanopoolmonitoring.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.recycler.accounts.AccountAdapter
import by.lebedev.nanopoolmonitoring.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.room.DataBase
import by.lebedev.nanopoolmonitoring.room.entity.Account
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.accounts_layout.*
import javax.inject.Inject


class AccountsActivity : AppCompatActivity() {
    private var back_pressed: Long = 0

    lateinit var mAdView: AdView

    @Inject
    lateinit var accountLocalList: AccountLocalList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accounts_layout)

        MobileAds.initialize(this, "ca-app-pub-9699134137611847~3977929109")
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-9699134137611847/6712027369"

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
                setupRecycler(result)
                accountLocalList.list = result as ArrayList<Account>
                progressAccountLoad.visibility = View.GONE
            }
    }

    override fun onResume() {
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