package by.lebedev.nanopoolmonitoring.retrofit.entity.workers

import java.text.SimpleDateFormat
import java.util.*

class DataWorkers(
    val id: String,
    val hashrate: Long,
    val lastShare: Long
) {


    fun convertLongToTime(time: Long): String {
        val date = Date(time * 1000)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return format.format(date)
    }
}