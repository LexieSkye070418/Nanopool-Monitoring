package by.lebedev.nanopoolmonitoring.fragments.retrofit.entity.payment

data class Payments (
    val status:Boolean,
    val data: ArrayList<DataPayments>
)