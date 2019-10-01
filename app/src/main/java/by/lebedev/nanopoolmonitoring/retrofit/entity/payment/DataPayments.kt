package by.lebedev.nanopoolmonitoring.retrofit.entity.payment

import java.text.SimpleDateFormat
import java.util.*


class DataPayments(
    val date: Long,
    val txHash: String,
    val amount: Double,
    val confirmed: Boolean
) {

    fun transform(confirmed: Boolean): String {
        if (confirmed) {
            return "Confirmed"
        } else return "Unconfirmed"
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time*1000)
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return sdf.format(date)


    }
}