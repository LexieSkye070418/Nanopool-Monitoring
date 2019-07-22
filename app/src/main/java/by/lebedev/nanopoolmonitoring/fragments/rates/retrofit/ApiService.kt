package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit


import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCapInfo
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("v1/cryptocurrency/listings/latest")
    fun loadData(): Single<CoinCapInfo>


}