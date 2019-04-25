package by.lebedev.nanopoolmonitoring.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import by.lebedev.nanopoolmonitoring.room.entity.Account

@Database(entities = arrayOf(Account::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDAO
}