package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity

data class CoinCap(
    var id: Int,
    var name: String,
    var symbol: String,
    var quote : Quote,


//    var url: String = "http://coincap.io/images/coins/$id.png"
//    var url: String = "https://static.coincap.io/assets/icons/$id@2x.png"
    var url: String = "https://s2.coinmarketcap.com/static/img/coins/32x32/$id.png"

//            https://static.coincap.io/assets/icons/btc@2x.png
//            https://static.coincap.io/assets/icons/eth@2x.png
)