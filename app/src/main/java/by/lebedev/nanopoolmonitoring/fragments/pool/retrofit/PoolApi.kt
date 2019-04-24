package by.lebedev.nanopoolmonitoring.fragments.pool.retrofit

import by.lebedev.nanopoolmonitoring.fragments.pool.retrofit.entity.Hashrate
import by.lebedev.nanopoolmonitoring.fragments.pool.retrofit.entity.Miners
import by.lebedev.nanopoolmonitoring.fragments.pool.retrofit.entity.Price
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PoolApi {

    @GET("{coin}/prices")
    fun getPrice(@Path("coin")coin: String): Single<Price>

    @GET("{coin}/pool/hashrate")
    fun getHashrate(@Path("coin")coin: String): Single<Hashrate>

    @GET("{coin}/pool/activeminers")
    fun getMiners(@Path("coin")coin: String): Single<Miners>
}