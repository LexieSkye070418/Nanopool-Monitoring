package by.lebedev.nanopoolmonitoring.dagger.provider

import by.lebedev.nanopoolmonitoring.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoring.dagger.PoolCoins
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import dagger.Module
import dagger.Provides

@Module
class MagicBoxModule {

    @Provides
    fun getTabIntentInstance():CoinWalletTempData{
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