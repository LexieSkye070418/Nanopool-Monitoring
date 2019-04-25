package by.lebedev.nanopoolmonitoring.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey
    val id: Long,
    val coin: String,
    val wallet: String
)
