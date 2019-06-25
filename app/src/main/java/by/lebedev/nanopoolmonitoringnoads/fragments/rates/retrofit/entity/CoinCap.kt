package by.lebedev.nanopoolmonitoringnoads.fragments.rates.retrofit.entity

data class CoinCap(
    val id: String = "",
    var symbol: String = "",
    var price_usd: Double = 0.0,
    var percent_change_24h: Double = 0.0,
    var url: String = "http://coincap.io/images/coins/$id.png"

)