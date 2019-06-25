package by.lebedev.nanopoolmonitoringnoads.room

import android.arch.persistence.room.Room
import android.content.Context


class DataBase private constructor(context: Context) {

    val db: AppDatabase

    init {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database"
        )
            .build()
    }

    companion object {
        private var instance: DataBase? = null

        @Synchronized
        fun getInstance(context: Context): DataBase {
            if (instance == null) {
                instance = DataBase(context)
            }
            return instance as DataBase
        }
    }
}