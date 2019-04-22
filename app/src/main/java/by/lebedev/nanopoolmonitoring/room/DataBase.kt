package by.lebedev.nanopoolmonitoring.room

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class DataBase : AppCompatActivity() {

    var database: AppDatabase? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .build()
    }

    companion object {
        var instance = DataBase()
    }
}