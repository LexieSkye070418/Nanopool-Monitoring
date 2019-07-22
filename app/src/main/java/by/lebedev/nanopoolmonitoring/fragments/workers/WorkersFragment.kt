package by.lebedev.nanopoolmonitoring.fragments.workers

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
import by.lebedev.nanopoolmonitoring.dagger.TabIntent
import by.lebedev.nanopoolmonitoring.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoring.fragments.workers.recycler.WorkersAdapter
import by.lebedev.nanopoolmonitoring.retrofit.entity.workers.DataWorkers
import by.lebedev.nanopoolmonitoring.retrofit.provideApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_workers.*
import javax.inject.Inject


class WorkersFragment : Fragment() {

    @Inject
    lateinit var tabIntent: TabIntent

    var coin: String = ""
    var wallet: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(by.lebedev.nanopoolmonitoring.R.layout.fragment_workers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()
            ?.setBackgroundDrawableResource(by.lebedev.nanopoolmonitoring.R.drawable.nanopool_background)


        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        //Поиск по workers
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


                if (!TextUtils.isEmpty(s)) {
                    tabIntent.filteredLocalWorkersList.clear()

                    for (i in 0 until tabIntent.localWorkersList.size) {
                        if (tabIntent.localWorkersList.get(i).id.contains(s.toString(), true)) {
                            tabIntent.filteredLocalWorkersList.add(tabIntent.localWorkersList.get(i))
                        }
                    }
                    setupRecycler(tabIntent.filteredLocalWorkersList)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        getWorkers()
    }

    fun getWorkers() {
        val d = provideApi().getWorkers(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                if (!result.data.isEmpty() && progressWorkers != null && workers_recycle != null && workersTotal != null) {
                    tabIntent.localWorkersList = result.data
                    progressWorkers.visibility = View.INVISIBLE
                    setupRecycler(result.data)
                    workersTotal.setText("Workers total: "+result.data.size)
                    workersAlive.setText("Alive: "+countAlive(result.data))
                    workersDead.setText("Dead: "+countDead(result.data))
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
        workers_recycle.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(view?.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        workers_recycle.layoutManager = layoutManager
        workers_recycle.adapter = WorkersAdapter(workers)
        (workers_recycle.adapter as WorkersAdapter).notifyDataSetChanged()
    }

    override fun onPause() {
        searchText.setText("")
        getWorkers()
        super.onPause()
        tabIntent.filteredLocalWorkersList.clear()
        tabIntent.localWorkersList.clear()
    }

    fun countAlive(workerList:ArrayList<DataWorkers>):Int{
        var count =0
        for (i in 0 until workerList.size){
            if (workerList.get(i).hashrate != 0L){
                count++
            }
        }
        return count
    }

    fun countDead(workerList:ArrayList<DataWorkers>):Int{
        var count =0
        for (i in 0 until workerList.size){
            if (workerList.get(i).hashrate == 0L){
                count++
            }
        }
        return count
    }

}