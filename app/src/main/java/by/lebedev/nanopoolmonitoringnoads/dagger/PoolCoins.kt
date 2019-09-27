package by.lebedev.nanopoolmonitoringnoads.dagger

import javax.inject.Singleton

@Singleton
class PoolCoins private constructor() {
    val list = ArrayList<String>()

    init {
        list.add("eth")
        list.add("etc")
        list.add("zec")
        list.add("xmr")
        list.add("pasc")
        list.add("rvn")
        list.add("grin29")
    }

    fun fullName(coinId: Int): String {
        when (coinId) {
            0 -> {
                return "Ethereum"
            }
            1 -> {
                return "Ethereum classic"
            }
            2 -> {
                return "Zed cash"
            }
            3 -> {
                return "Monero"
            }
            4 -> {
                return "Pascal"
            }
            5 -> {
                return "Raven coin"
            }
            6 -> {
                return "Grin-29"
            }
            else -> return "Ethereum"
        }
    }

    private object Holder {
        val INSTANCE = PoolCoins()
    }

    companion object {
        val instance: PoolCoins by lazy { Holder.INSTANCE }
    }
}