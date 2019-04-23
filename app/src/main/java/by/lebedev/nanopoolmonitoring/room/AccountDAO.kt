package by.lebedev.nanopoolmonitoring.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface AccountDAO {
    @Query("SELECT * FROM account")
    fun getAll(): List<Account>

    @Insert
    fun insert(vararg account: Account)

    @Delete
    fun delete(account: Account)
}