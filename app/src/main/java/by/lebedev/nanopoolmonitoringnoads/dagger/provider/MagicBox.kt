package by.lebedev.nanopoolmonitoringnoads.dagger.provider

import by.lebedev.nanopoolmonitoringnoads.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoringnoads.dagger.CoinWalletTempData
import by.lebedev.nanopoolmonitoringnoads.dagger.PoolCoins
import dagger.Component

@Component(modules = arrayOf(MagicBoxModule::class))

interface MagicBox {
    fun provideCoinWalletTempData(): CoinWalletTempData
    fun providePoolCoins(): PoolCoins
    fun provideAccountLocalList(): AccountLocalList
}