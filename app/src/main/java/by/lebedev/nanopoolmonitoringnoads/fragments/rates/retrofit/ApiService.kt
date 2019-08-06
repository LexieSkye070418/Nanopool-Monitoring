package by.lebedev.nanopoolmonitoringnoads.fragments.rates.retrofit


import by.lebedev.nanopoolmonitoringnoads.fragments.rates.retrofit.entity.CoinCapInfo
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("v1/cryptocurrency/listings/latest")
    fun loadData(): Single<CoinCapInfo>


}