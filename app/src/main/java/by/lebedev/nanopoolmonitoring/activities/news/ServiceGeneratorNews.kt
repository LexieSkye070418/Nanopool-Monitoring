package by.lebedev.nanopoolmonitoring.activities.news

import android.util.Log
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGeneratorNews {

        private val BASE_URL_NEWS = "https://cryptocontrol.io/api/v1/public/news/"
//        private val BASE_URL_NEWS = "https://api.coinmarketcap.com/v1/"

        // выставлены таймауты на соединение с сервером
//        private val httpClient = OkHttpClient.Builder()
//            .connectTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(10, TimeUnit.SECONDS)


        fun createNews(): ApiServiceNews {
            Log.e("AAA","api service news")
            val retrofitNews = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_NEWS)
                .build()
            return retrofitNews.create(ApiServiceNews::class.java)

        }
    }
