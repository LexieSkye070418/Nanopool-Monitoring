package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity

data class CoinCap(
    val id: Int = 1,
    var symbol: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var percent_change_24h: Double = 0.0,
//    var url: String = "http://coincap.io/images/coins/$id.png"
//    var url: String = "https://static.coincap.io/assets/icons/$id@2x.png"
    var url: String = "https://s2.coinmarketcap.com/static/img/coins/32x32/$id.png"

//            https://static.coincap.io/assets/icons/btc@2x.png
//            https://static.coincap.io/assets/icons/eth@2x.png
)