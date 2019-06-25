package by.lebedev.nanopoolmonitoringnoads.dagger.provider

import by.lebedev.nanopoolmonitoringnoads.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoringnoads.dagger.PoolCoins
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import dagger.Module
import dagger.Provides

@Module
class MagicBoxModule {

    @Provides
    fun getTabIntentInstance():TabIntent{
        return TabIntent.instance
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