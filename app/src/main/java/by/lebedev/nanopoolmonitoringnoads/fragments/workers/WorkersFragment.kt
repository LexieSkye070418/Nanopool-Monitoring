package by.lebedev.nanopoolmonitoringnoads.fragments.workers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.fragments.workers.recycler.WorkersAdapter
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.workers.DataWorkers
import by.lebedev.nanopoolmonitoringnoads.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_workers.*
import javax.inject.Inject


class WorkersFragment : Fragment() {

    @Inject
    lateinit var coinWalletTempData: CoinWalletTempData

    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(R.drawable.nanopool_background)


        val component = DaggerMagicBox.builder().build()
        coinWalletTempData = component.provideCoinWalletTempData()

        coin = coinWalletTempData.coin
        wallet = coinWalletTempData.wallet

        //Поиск по workers
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


                if (!TextUtils.isEmpty(s)) {
                    coinWalletTempData.filteredLocalWorkersList.clear()

                    for (i in 0 until coinWalletTempData.localWorkersList.size) {
                        if (coinWalletTempData.localWorkersList.get(i).id.contains(s.toString(), true)) {
                            coinWalletTempData.filteredLocalWorkersList.add(coinWalletTempData.localWorkersList.get(i))
                        }
                    }
                    setupRecycler(coinWalletTempData.filteredLocalWorkersList)
                } else {
                    setupRecycler(coinWalletTempData.localWorkersList)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        swipeRefreshWorkers.setColorSchemeResources(
            R.color.blue,
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.orange
        )
        swipeRefreshWorkers.setOnRefreshListener {
            workersRecycle.visibility = View.INVISIBLE
            workersAlive.visibility = View.INVISIBLE
            workersDead.visibility = View.INVISIBLE
            workersTotal.visibility = View.INVISIBLE
            getWorkers()
        }


        getWorkers()
    }

    fun getWorkers() {
        val d = provideApi().getWorkers(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result != null && result.status && !result.data.isEmpty() && progressWorkers != null && workersRecycle != null && workersTotal != null) {
                    coinWalletTempData.localWorkersList = result.data
                    progressWorkers.visibility = View.INVISIBLE
                    swipeRefreshWorkers.setRefreshing(false)
                    setupRecycler(result.data)
                    workersTotal.setText("Workers total: " + result.data.size)
                    workersAlive.setText("Alive: " + countAlive(result.data))
                    workersDead.setText("Dead: " + countDead(result.data))
                } else {
                    if (progressWorkers != null && textForError != null) {
                        progressWorkers.visibility = View.INVISIBLE
                        textForError.setText("Workers not found...")
                    }
                }
            }, {
                Log.e("err", it.message)
            })
    }

    fun setupRecycler(workers: ArrayList<DataWorkers>) {
        workersRecycle.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view?.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        workersRecycle.layoutManager = layoutManager
        workersRecycle.adapter = WorkersAdapter(workers)
        (workersRecycle.adapter as WorkersAdapter).notifyDataSetChanged()

        workersRecycle.visibility = View.VISIBLE
        workersTotal.visibility = View.VISIBLE
        workersAlive.visibility = View.VISIBLE
        workersDead.visibility = View.VISIBLE
    }

    override fun onPause() {
        searchText.setText("")
        getWorkers()
        super.onPause()
        coinWalletTempData.filteredLocalWorkersList.clear()
        coinWalletTempData.localWorkersList.clear()
    }

    fun countAlive(workerList: ArrayList<DataWorkers>): Int {
        var count = 0
        for (i in 0 until workerList.size) {
            if (workerList.get(i).hashrate != 0L) {
                count++
            }
        }
        return count
    }

    fun countDead(workerList: ArrayList<DataWorkers>): Int {
        var count = 0
        for (i in 0 until workerList.size) {
            if (workerList.get(i).hashrate == 0L) {
                count++
            }
        }
        return count
    }

}