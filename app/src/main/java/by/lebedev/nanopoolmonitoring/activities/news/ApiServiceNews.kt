package by.lebedev.nanopoolmonitoring.activities.news
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.Int as Int1

interface ApiServiceNews {


        @GET("coin/bitcoin")
        fun loadNews(@Query("key")key:String): Observable<List<News>>

//    fun productList(@Query("category") categoryId: Int1): Call<List<Product>>

}
