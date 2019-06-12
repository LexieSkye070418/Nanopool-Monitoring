package by.lebedev.nanopoolmonitoring.fragments.workers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
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
        return inflater.inflate(R.layout.fragment_workers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()

        coin = tabIntent.coin
        wallet = tabIntent.wallet

        getWorkers()
    }

    fun getWorkers() {
        val d = provideApi().getWorkers(coin, wallet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                Log.e("AAA",result.toString())


                if (!result.data.isEmpty()&&progressWorkers!=null&&workers_recycle!=null) {
                    progressWorkers.visibility = View.INVISIBLE
                    setupRecycler(result.data)
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
    }

}
