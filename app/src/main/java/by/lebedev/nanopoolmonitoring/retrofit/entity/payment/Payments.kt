package by.lebedev.nanopoolmonitoring.retrofit.entity.payment

data class Payments (
    val status:Boolean,
    val data: ArrayList<DataPayments>
)