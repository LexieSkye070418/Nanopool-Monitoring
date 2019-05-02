package by.lebedev.nanopoolmonitoring.retrofit.entity.dashboard

import java.text.SimpleDateFormat
import java.util.*

data class DataChart(
    val date: Double,
    val hashrate: Double
) {

    fun convertLongToTime(time: Double): String {
        val date = Date(time.toLong() * 1000)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return format.format(date)
    }
}