package by.lebedev.nanopoolmonitoring.fragments.retrofit.entity.dashboard

data class DataMiner(
    val balance: Double,
    val hashrate: Double,
    val avgHashrate: AvgHashrate
)