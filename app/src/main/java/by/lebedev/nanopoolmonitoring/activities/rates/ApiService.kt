package by.lebedev.nanopoolmonitoring.activities.rates

import io.reactivex.Observable
import retrofit2.http.GET

public interface ApiService {

    @GET("ticker")
    fun loadData(): Observable<List<CoinCap>>


}