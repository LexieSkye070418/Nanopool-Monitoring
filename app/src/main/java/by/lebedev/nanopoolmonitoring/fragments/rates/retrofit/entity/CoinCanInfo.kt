package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity

import by.lebedev.nanopoolmonitoring.retrofit.entity.chart.ChartData
import by.lebedev.nanopoolmonitoring.retrofit.entity.poolinfo.DataPool

data class CoinCanInfo (
    val status :Boolean,
    val data : ArrayList<CoinCap>
)