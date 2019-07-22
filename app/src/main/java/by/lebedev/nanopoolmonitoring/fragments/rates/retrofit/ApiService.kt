package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit


import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCanInfo
import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCap
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

public interface ApiService {

    @GET("v1/cryptocurrency/listings/latest")
    fun loadData(@Query("key") key: String): Single<CoinCanInfo>


}