package by.lebedev.nanopoolmonitoringnoads.fragments.news.retrofit

import by.lebedev.nanopoolmonitoringnoads.fragments.news.retrofit.entity.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.Int as Int1

interface ApiServiceNews {


        @GET("coin/bitcoin")
        fun loadNews(@Query("key")key:String): Observable<List<News>>


}
