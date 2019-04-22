package by.lebedev.nanopoolmonitoring.activities.news

import by.lebedev.nanopoolmonitoring.activities.course.CoinCap
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiServiceNews {

        @GET("bitcoin?language=ru=6bb2d102680be65b5d540eb15eb2c58a")
        fun loadNews(): Observable<List<News>>


    }
