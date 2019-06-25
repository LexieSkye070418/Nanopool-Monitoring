package by.lebedev.nanopoolmonitoringnoads.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import by.lebedev.nanopoolmonitoringnoads.room.entity.Account

@Database(entities = arrayOf(Account::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDAO
}