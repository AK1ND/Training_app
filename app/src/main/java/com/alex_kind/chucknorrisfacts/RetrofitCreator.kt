package com.alex_kind.chucknorrisfacts

import com.alex_kind.chucknorrisfacts.const.BASE_URL
import com.alex_kind.chucknorrisfacts.model.Categories
import com.alex_kind.chucknorrisfacts.model.ModelMain
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitCreator {

    fun getRetrofit(): RetrofitService {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(RetrofitService::class.java)
        return retrofit
    }

    interface RetrofitService {

        @GET("random")
        fun random(
            @Query("category") category: String
        ): Call<ModelMain>

        @GET("categories")
        fun categories(): Call<Categories>
    }
}