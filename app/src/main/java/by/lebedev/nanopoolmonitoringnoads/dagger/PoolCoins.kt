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
        list.add("etn")
        list.add("rvn")
        list.add("grin29")
    }

    private object Holder {
        val INSTANCE = PoolCoins()
    }

    companion object {
        val instance: PoolCoins by lazy { Holder.INSTANCE }
    }
}