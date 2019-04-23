package by.lebedev.nanopoolmonitoring.room

import android.app.Application
import android.arch.persistence.room.Room
import android.support.v4.app.Fragment

class DataBase : Fragment() {

    lateinit var db: AppDatabase

    init {
        db = Room.databaseBuilder(
            context?.applicationContext!!,
            AppDatabase::class.java, "database"
        ).build()

    }


    companion object {
        val instances = DataBase()
    }
}