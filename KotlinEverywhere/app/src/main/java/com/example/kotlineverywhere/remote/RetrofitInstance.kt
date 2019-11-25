package com.example.kotlineverywhere.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.myjson.com/"
    var service: MemberService

    init { //like constructor, firstly this method works when started
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(MemberService::class.java)
    }
}