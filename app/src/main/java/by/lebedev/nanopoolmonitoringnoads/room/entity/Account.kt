package by.lebedev.nanopoolmonitoringnoads.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Account(
    @PrimaryKey
    val id: Long,
    var coin: String,
    var wallet: String
)
