package by.lebedev.nanopoolmonitoring.retrofit

import by.lebedev.nanopoolmonitoring.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetProvider internal fun provideApi(): PoolApi {

    val clientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
    }
    val okhttp = clientBuilder.build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nanopool.org/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okhttp)
        .build()

    return retrofit.create(PoolApi::class.java)
}