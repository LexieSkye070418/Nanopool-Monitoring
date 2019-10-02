package by.lebedev.nanopoolmonitoringnoads.dagger.provider

import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData

import by.lebedev.nanopoolmonitoringnoads.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoringnoads.dagger.PoolCoins

import dagger.Module
import dagger.Provides

@Module
class MagicBoxModule {

    @Provides
    fun getTabIntentInstance(): CoinWalletTempData {
        return CoinWalletTempData.INSTANCE
    }

    @Provides
    fun getPoolCoinsInstance():PoolCoins{
        return PoolCoins.instance
    }

    @Provides
    fun getAccountLocalListInstance():AccountLocalList{
        return AccountLocalList.instance
    }
}