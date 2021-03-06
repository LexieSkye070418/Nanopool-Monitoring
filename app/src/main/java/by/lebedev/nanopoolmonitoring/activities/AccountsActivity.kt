package by.lebedev.nanopoolmonitoring.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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
import kotlinx.android.synthetic.main.account_layout.*
import javax.inject.Inject


class AccountsActivity : AppCompatActivity() {
    private var back_pressed: Long = 0

    lateinit var mAdView: AdView

    @Inject
    lateinit var accountLocalList: AccountLocalList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_layout)
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Network unavailable...", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Check Internet connection...", Toast.LENGTH_LONG).show()
        }


        MobileAds.initialize(this, "ca-app-pub-1501215034144631~3780667725")

        val adView = AdView(this)
        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-1501215034144631/7368226777"


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

        swipeRefreshAccounts.setColorSchemeResources(
            R.color.blue,
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.orange
        )
        swipeRefreshAccounts.setOnRefreshListener {
            recycleAccounts.visibility = View.INVISIBLE
            getAllDatabase()
        }


        getAllDatabase()

        removeAdsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=by.lebedev.nanopoolmonitoringnoads")
            startActivity(intent)
        }
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
                    Thread.sleep(200)
                    swipeRefreshAccounts.setRefreshing(false)
                    recycleAccounts.visibility = View.VISIBLE
                    noAccountText.visibility = View.VISIBLE

                } else {
                    Thread.sleep(200)
                    swipeRefreshAccounts.setRefreshing(false)
                    recycleAccounts.visibility = View.VISIBLE
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

        Log.e("FF", "onResume")
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}