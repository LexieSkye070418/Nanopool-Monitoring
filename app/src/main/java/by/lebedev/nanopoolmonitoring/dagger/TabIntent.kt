package by.lebedev.nanopoolmonitoring.dagger

class TabIntent private constructor() {

    var coin: String = ""
    var wallet: String = ""


    private object Holder {
        val INSTANCE = TabIntent()
    }

    companion object {
        val instance: TabIntent by lazy { Holder.INSTANCE }
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
            "etn" -> {
                return "Electroneum"
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
            "etn" -> {
                return "kH/s"
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
}