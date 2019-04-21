package by.lebedev.nanopoolmonitoring.activities.course

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.course.ServiceGenerator.create1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_four.*
import java.net.URI.create

class FourFragment : Fragment() {

    private lateinit var adapter: CoinAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_four, container, false)
//        getD()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getD()
    }

    fun getD() {
        val disposables = create1().loadData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.map {
                    it
                    it.url = "http://coincap.io/images/coins/${it.id}.png"
                    it.price_usd = Math.round(it.price_usd *100.0)/100.0
                }
                setupRecycler(result)
                Log.e("AAA", "забрал лист")
            },
                { error -> Log.e("AAA", "не забрал!!!") })
    }


    fun setupRecycler(list: List<CoinCap>) {

        recycleView.layoutManager = LinearLayoutManager(context)

        adapter = CoinAdapter(list)

        recycleView.adapter = adapter
    }
}
//package com.example.coincap
//
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import com.example.coincap.adapters.CoinAdapter
//import com.example.coincap.api.ServiceGenerator
//import kotlinx.android.synthetic.main.activity_main.*
//import android.os.AsyncTask.execute
//import android.telecom.Call
//import android.util.Log
//import com.example.coincap.api.ApiService
//import com.example.coincap.api.ServiceGenerator.create
//import com.example.coincap.models.CoinCap
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import java.util.*
//import kotlin.collections.ArrayList
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var adapter: CoinAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        getD()
//    }
//
//
//    fun getD(){
//        val d =  create().loadData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                    result ->result.map {it
//                it.url = "http://coincap.io/images/coins/${it.id}.png"
//            }
//                setupRecycler(result)
//                Log.e("AAA","забрал лист")
//            },
//                { error -> Log.e("AAA", "не забрал!!!") })
//    }
//
//
//    fun setupRecycler(list: List<CoinCap>) {
//
//        recycleView.layoutManager = LinearLayoutManager(this)
//
//        adapter = CoinAdapter(list)
//
//        recycleView.adapter = adapter
//    }
//}