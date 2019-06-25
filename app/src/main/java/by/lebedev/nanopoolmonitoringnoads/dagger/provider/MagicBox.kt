package by.lebedev.nanopoolmonitoringnoads.dagger.provider

import by.lebedev.nanopoolmonitoringnoads.dagger.AccountLocalList
import by.lebedev.nanopoolmonitoringnoads.dagger.PoolCoins
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import dagger.Component

@Component(modules = arrayOf(MagicBoxModule::class))

interface MagicBox {
    fun provideTabIntent(): TabIntent
    fun providePoolCoins(): PoolCoins
    fun provideAccountLocalList(): AccountLocalList
}