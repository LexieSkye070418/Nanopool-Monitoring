package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit


import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCapInfo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

public interface ApiService {

    @GET("v1/cryptocurrency/listings/latest")
    fun loadData(@Query("x-cmc_pro_api_key") key: String): Single<CoinCapInfo>


}