package by.lebedev.nanopoolmonitoring.dagger.provider

import by.lebedev.nanopoolmonitoring.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoring.dagger.PoolCoins
import by.lebedev.nanopoolmonitoring.dagger.CoinWalletTempData
import dagger.Component

@Component(modules = arrayOf(MagicBoxModule::class))

interface MagicBox {
    fun provideTabIntent(): CoinWalletTempData
    fun providePoolCoins(): PoolCoins
    fun provideAccountLocalList(): AccountLocalList
}