package by.lebedev.nanopoolmonitoringnoads.room

import android.arch.persistence.room.*
import by.lebedev.nanopoolmonitoringnoads.room.entity.Account

import io.reactivex.Single

@Dao
interface AccountDAO {
    @Query("SELECT * FROM account")
    fun getAll(): Single<List<Account>>

    @Insert
    fun insert(vararg account: Account)

    @Delete
    fun delete(account: Account)

    @Update
    fun modify(account: Account)

}