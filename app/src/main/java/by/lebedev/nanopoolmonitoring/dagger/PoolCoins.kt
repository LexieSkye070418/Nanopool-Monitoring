package by.lebedev.nanopoolmonitoring.dagger

class PoolCoins private constructor() {
    val list = ArrayList<String>()

    init {
        list.add("ETH")
        list.add("ETC")
        list.add("ZEC")
        list.add("XMR")
        list.add("PASC")
        list.add("ETN")
        list.add("RVN")
        list.add("GRIN")
    }

    private object Holder {
        val INSTANCE = PoolCoins()
    }

    companion object {
        val instance: PoolCoins by lazy { Holder.INSTANCE }
    }
}