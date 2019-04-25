package by.lebedev.nanopoolmonitoring.fragments.news.retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGeneratorNews {

        private val BASE_URL_NEWS = "https://cryptocontrol.io/api/v1/public/news/"

        fun createNews(): ApiServiceNews {
            Log.e("AAA","api service news")
            val retrofitNews = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL_NEWS)
                .build()
            return retrofitNews.create(ApiServiceNews::class.java)
        }
    }