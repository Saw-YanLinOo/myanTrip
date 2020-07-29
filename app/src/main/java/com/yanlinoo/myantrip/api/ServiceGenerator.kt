package com.yanlinoo.myantrip.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private  val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URLS)
        .build()

    fun<T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}