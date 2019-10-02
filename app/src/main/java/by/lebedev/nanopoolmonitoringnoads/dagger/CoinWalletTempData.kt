package by.lebedev.nanopoolmonitoringnoads.dagger

import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.workers.DataWorkers
import javax.inject.Singleton

@Singleton
class CoinWalletTempData private constructor() {

    var coin: String = ""
    var wallet: String = ""
    var localWorkersList = ArrayList<DataWorkers>()
    var filteredLocalWorkersList = ArrayList<DataWorkers>()


    private object Holder {
        val INSTANCE = CoinWalletTempData()
    }

    companion object {
        val INSTANCE: CoinWalletTempData by lazy { Holder.INSTANCE }
    }

    fun fullName(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "Ethereum"
            }
            "etc" -> {
                return "Ethereum Classic"
            }
            "zec" -> {
                return "ZCash"
            }
            "xmr" -> {
                return "Monero"
            }
            "pasc" -> {
                return "Pascal"
            }

            "rvn" -> {
                return "Raven"
            }
            "grin29" -> {
                return "Grin-29"
            }
            else -> "N/A"
        }
    }

    fun shortNameFromSelector(coinId: Int): String {
        return when (coinId) {
            0 -> {
                return "eth"
            }
            1 -> {
                return "etc"
            }
            2 -> {
                return "zec"
            }
            3 -> {
                return "xmr"
            }
            4 -> {
                return "pasc"
            }
            5 -> {
                return "rvn"
            }
            6 -> {
                return "grin29"
            }
            else -> "N/A"
        }
    }


    fun getHashType(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "Gh/s"
            }
            "etc" -> {
                return "Gh/s"
            }
            "zec" -> {
                return "KSol/s"
            }
            "xmr" -> {
                return "kH/s"
            }
            "pasc" -> {
                return "KH/s"
            }
            "rvn" -> {
                return "Gh/s"
            }
            "grin29" -> {
                return "Kgp/s"
            }
            else -> "N/A"
        }
    }

    fun getWorkerHashType(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "Mh/s"
            }
            "etc" -> {
                return "Mh/s"
            }
            "zec" -> {
                return "Sol/s"
            }
            "xmr" -> {
                return "H/s"
            }
            "pasc" -> {
                return "H/s"
            }
            "rvn" -> {
                return "Mh/s"
            }
            "grin29" -> {
                return "Gp/s"
            }
            else -> "N/A"
        }
    }

    fun getWorkerHashTypeHigh(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "Gh/s"
            }
            "etc" -> {
                return "Gh/s"
            }
            "zec" -> {
                return "KSol/s"
            }
            "xmr" -> {
                return "KH/s"
            }
            "pasc" -> {
                return "KH/s"
            }
            "rvn" -> {
                return "Gh/s"
            }
            "grin29" -> {
                return "Tp/s"
            }
            else -> "N/A"
        }
    }

    fun getAuthors(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "Vitalik Buterin, Gavin Wood, Joseph Lubin"
            }
            "etc" -> {
                return "Project fork of Ethereum"
            }
            "zec" -> {
                return "William Scott"
            }
            "xmr" -> {
                return "Riccardo Spagni, Francisco Cabañas"
            }
            "pasc" -> {
                return "Molina"
            }
            "rvn" -> {
                return "Project fork of Bitcoin"
            }
            "grin29" -> {
                return "Tom Elvis Jedusor"
            }
            else -> "N/A"
        }
    }

    fun getRelease(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "30 July 2015"
            }
            "etc" -> {
                return "30 July 2015"
            }
            "zec" -> {
                return "28 October 2016"
            }
            "xmr" -> {
                return "18 April 2014"
            }
            "pasc" -> {
                return "July 2016"
            }
            "rvn" -> {
                return "3 January 2018"
            }
            "grin29" -> {
                return "15 January 2019"
            }
            else -> "N/A"
        }
    }

    fun getWrittenIn(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "C++, Go, Rust"
            }
            "etc" -> {
                return "C++, Go, Rust, Scala"
            }
            "zec" -> {
                return "С++"
            }
            "xmr" -> {
                return "С++"
            }
            "pasc" -> {
                return "Pascal"
            }
            "rvn" -> {
                return "С++"
            }
            "grin29" -> {
                return "Rust"
            }
            else -> "N/A"
        }
    }

    fun getWebsite(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "ethereum.org"
            }
            "etc" -> {
                return "ethereumclassic.org"
            }
            "zec" -> {
                return "z.cash"
            }
            "xmr" -> {
                return "getmonero.org"
            }
            "pasc" -> {
                return "pascalcoin.org"
            }
            "rvn" -> {
                return "ravencoin.org"
            }
            "grin29" -> {
                return "grin-tech.org"
            }
            else -> "N/A"
        }
    }

    fun getPoolFee(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "1%"
            }
            "etc" -> {
                return "1%"
            }
            "zec" -> {
                return "1%"
            }
            "xmr" -> {
                return "1%"
            }
            "pasc" -> {
                return "2%"
            }
            "rvn" -> {
                return "1%"
            }
            "grin29" -> {
                return "1%"
            }
            else -> "N/A"
        }
    }

    fun getPayoutScheme(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "PPLNS (20 minutes)"
            }
            "etc" -> {
                return "PPLNS (20 minutes)"
            }
            "zec" -> {
                return "PPLNS (3 hours)"
            }
            "xmr" -> {
                return "PPLNS (6 hours)"
            }
            "pasc" -> {
                return "PPLNS (1 hour)"
            }
            "rvn" -> {
                return "PPLNS (6 hours)"
            }
            "grin29" -> {
                return "PPLNS (6 hours)"
            }
            else -> "N/A"
        }
    }

    fun getBlockValidation(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "50 blocks"
            }
            "etc" -> {
                return "100 blocks"
            }
            "zec" -> {
                return "10 blocks"
            }
            "xmr" -> {
                return "10 blocks"
            }
            "pasc" -> {
                return "10 blocks"
            }
            "rvn" -> {
                return "20 blocks"
            }
            "grin29" -> {
                return "1500 blocks"
            }
            else -> "N/A"
        }
    }

    fun getPayoutLimit(coin: String): String {
        return when (coin) {
            "eth" -> {
                return "0.05 ETH and up to 20 ETH"
            }
            "etc" -> {
                return "0.1 ETC and up to 100 ETC"
            }
            "zec" -> {
                return "0.01 ZEC that can be raised up to 10 ZEC"
            }
            "xmr" -> {
                return "0.1 XMR and up to 10 XMR"
            }
            "pasc" -> {
                return "0.5 PASC and up to 100 PASC"
            }
            "rvn" -> {
                return "50 RVN up to 50000 RVN"
            }
            "grin29" -> {
                return "1 GRIN up to 50 GRIN"
            }
            else -> "N/A"
        }
    }

}