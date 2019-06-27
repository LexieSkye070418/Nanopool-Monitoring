package by.lebedev.nanopoolmonitoring.fragments.rates.retrofit

import by.lebedev.nanopoolmonitoring.fragments.rates.retrofit.entity.CoinCap
import io.reactivex.Observable
import retrofit2.http.GET

public interface ApiService {

    @GET("ticker")
    fun loadData(): Observable<List<CoinCap>>


}